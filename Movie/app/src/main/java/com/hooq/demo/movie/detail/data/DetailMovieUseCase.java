package com.hooq.demo.movie.detail.data;

import android.content.Context;

import com.hooq.demo.movie.common.util.DefaultSubscriber;
import com.hooq.demo.movie.common.util.UseCase;
import com.hooq.demo.movie.detail.data.entity.MovieDetailEntity;

public class DetailMovieUseCase extends UseCase {

    private DetailMovieRepository repository;

    public DetailMovieUseCase(Context context) {
        repository = new DetailMovieRepository(context);
    }

    public void getMovieDetail(final int movieId, DefaultSubscriber<MovieDetailEntity> subscriber) {
        execute(repository.getMovieDetail(movieId), subscriber);
    }
}
