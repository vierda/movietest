package com.hooq.demo.movie.movielist.data;

import android.content.Context;

import com.hooq.demo.movie.common.data.entity.MovieResults;

import rx.Observable;
import rx.functions.Func1;

public class MovieListRepository {

    private MovieListNetworkRepository networkRepository;
    private MovieListDatabaseRepository databaseRepository;

    public MovieListRepository(Context context) {
        networkRepository = new MovieListNetworkRepository(context);
        databaseRepository = new MovieListDatabaseRepository();
    }

    Observable<MovieResults> loadMovies(int page) {
        return networkRepository.loadMovies(page).concatMap(new Func1<MovieResults, Observable<? extends MovieResults>>() {
            @Override
            public Observable<? extends MovieResults> call(MovieResults movieResults) {
                return databaseRepository.saveMovies(movieResults);
            }
        });

    }

}
