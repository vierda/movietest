package com.hooq.demo.movie.common.util;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public abstract class UseCase {
    protected final String TAG;

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;


    protected final List<Subscription> subscription = new ArrayList<>();


    protected UseCase() {
        this.threadExecutor = new JobExecutor();
        this.postExecutionThread = new BackgroundThread();

        TAG = this.getClass().getSimpleName();
    }


    public Subscription execute(Observable observable, Subscriber ucSubscriber) {
        return execute(observable, ucSubscriber, true);
    }


    @SuppressWarnings("unchecked")
    public Subscription execute(Observable observable, Subscriber subscriber, boolean autoUnsubscribe) {
        Subscription subs = null;
        try {
            subs = observable
                    .onBackpressureBuffer()
                    .subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.getScheduler())
                    .subscribe(subscriber);
        } catch (Exception ex) {
            // no-op
        }

        if (autoUnsubscribe && subs != null) {
            try {
                synchronized (subscription) {
                    this.subscription.add(subs);
                }

                subs = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return subs;
    }


    public void unsubscribe(boolean async) {
        do {
            Thread worker = new Thread() {
                @Override
                public void run() {
                    synchronized (subscription) {
                        for (Subscription sub : subscription) {
                            if (!sub.isUnsubscribed())
                                sub.unsubscribe();
                        }
                    }

                    Log.i(TAG, "Un-subscribed all subscriptions ...");
                }
            };

            if (async) {
                worker.start();
                break;
            }

            worker.run();
        } while (false);
    }


    public void unsubscribe() {
        unsubscribe(true);
    }

    private SparseArray<Subscription> timerSubscriptions;

    public synchronized boolean startTimer(int id, long duration, Subscriber<? super Long> subscriber) {
        boolean blRet = false;

        do {
            if (duration <= 0) break;
            Subscription subscription = getTimerSubscription(id);
            if (subscription != null) {
                if (subscription.isUnsubscribed())
                    subscription.unsubscribe();

                timerSubscriptions.remove(id);
            }

            if (timerSubscriptions == null) timerSubscriptions = new SparseArray<>();

            try {
                subscription = Observable.timer(duration, TimeUnit.MILLISECONDS)
                        .onBackpressureBuffer()
                        .subscribeOn(Schedulers.from(threadExecutor))
                        .observeOn(postExecutionThread.getScheduler())
                        .subscribe(subscriber);

                timerSubscriptions.put(id, subscription);
                blRet = true;
            } catch (Exception ex) {
                break;
            }
        } while (false);

        return blRet;
    }

    public synchronized boolean stopTimer(int id) {
        boolean blRet = false;

        do {
            Subscription subscription = getTimerSubscription(id);
            if (subscription == null) break;

            try {
                subscription.unsubscribe();
            } catch (Exception ex) {
            }

            timerSubscriptions.remove(id);

            blRet = true;
        } while (false);

        return blRet;
    }

    private synchronized Subscription getTimerSubscription(int id) {
        Subscription subscription = null;

        if (timerSubscriptions != null)
            subscription = timerSubscriptions.get(id);

        return subscription;
    }
}
