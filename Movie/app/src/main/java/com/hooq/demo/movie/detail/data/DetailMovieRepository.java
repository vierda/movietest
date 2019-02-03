package com.hooq.demo.movie.detail.data;

import android.content.Context;

import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.detail.data.entity.MovieDetailEntity;

import rx.Observable;
import rx.functions.Func1;

public class DetailMovieRepository {

    private DetailMovieNetworkRepository networkRepository;
    private DetailMovieDatabaseRepository databaseRepository;

    public DetailMovieRepository(Context context) {
        networkRepository = new DetailMovieNetworkRepository(context);
        databaseRepository = new DetailMovieDatabaseRepository();
    }

    Observable<MovieResults> getSimilarMovies(int movieId) {
        return networkRepository.loadSimilarMovies(movieId).concatMap(new Func1<MovieResults, Observable<? extends MovieResults>>() {
            @Override
            public Observable<? extends MovieResults> call(MovieResults movieResults) {
                return databaseRepository.saveSimilarMovies(movieResults);
            }
        });
    }

    Observable<MovieDetailEntity> getMovieDetail(final int movieId) {
        return getSimilarMovies(movieId).concatMap(new Func1<MovieResults, Observable<? extends MovieDetailEntity>>() {
            @Override
            public Observable<? extends MovieDetailEntity> call(final MovieResults movieResults) {
                return databaseRepository.getMovie(movieId).concatMap(new Func1<Movie, Observable<? extends MovieDetailEntity>>() {
                    @Override
                    public Observable<? extends MovieDetailEntity> call(Movie movie) {
                        return databaseRepository.convertMovieToMovieDetailEntity(movie,movieResults);
                    }
                });
            }
        });
    }
}
