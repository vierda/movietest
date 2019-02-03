package com.hooq.demo.movie.detail.data;

import android.content.Context;

import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.common.network.NetworkException;
import com.hooq.demo.movie.common.network.NetworkRequest;
import com.hooq.demo.movie.common.network.Response;
import com.hooq.demo.movie.common.network.ResponseHandler;

import rx.Observable;
import rx.Subscriber;

public class DetailMovieNetworkRepository {

    private NetworkRequest networkRequest;

    public DetailMovieNetworkRepository(Context context) {
        networkRequest = new NetworkRequest(context);
    }

    Observable<MovieResults> loadSimilarMovies(final int movieId) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<MovieResults>() {
            @Override
            public void call(final Subscriber<? super MovieResults> subscriber) {
                networkRequest.getSimilarMovie(movieId,2, new ResponseHandler<MovieResults>() {
                    @Override
                    public void onReceive(Response<MovieResults> response) {
                        if (response != null && response.getData() != null) {
                            subscriber.onNext(response.getData());
                        } else {
                            subscriber.onError(new NetworkException(NetworkException.SERVER_ERROR, "no message"));
                        }

                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
