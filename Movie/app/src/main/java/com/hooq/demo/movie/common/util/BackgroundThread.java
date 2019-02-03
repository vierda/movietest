package com.hooq.demo.movie.common.util;


import rx.Scheduler;
import rx.schedulers.Schedulers;

public class BackgroundThread implements PostExecutionThread{

    @Override
    public Scheduler getScheduler() {
        return Schedulers.computation();
    }
}
