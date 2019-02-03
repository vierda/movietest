package com.hooq.demo.movie.movielist.data;

import android.util.Log;

import com.hooq.demo.movie.MovieApplication;
import com.hooq.demo.movie.common.data.database.AppDatabase;
import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.data.entity.MovieResults;

import rx.Observable;
import rx.Subscriber;

public class MovieListDatabaseRepository {

    private AppDatabase appDatabase = MovieApplication.getInstance().getDatabase();

    Observable<MovieResults> saveMovies(final MovieResults movieResults) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<MovieResults>() {
            @Override
            public void call(final Subscriber<? super MovieResults> subscriber) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.MovieResultsDao().insertAll(movieResults);

                        if (movieResults.getResults() != null || !movieResults.getResults().isEmpty()) {
                            Movie[] movieArr = new Movie[movieResults.getResults().size()];
                            movieArr = movieResults.getResults().toArray(movieArr);
                            appDatabase.MovieDao().insertAll(movieArr);
                        }

                        subscriber.onNext(movieResults);
                        subscriber.onCompleted();
                    }
                }).start();
            }
        });
    }
}
