package com.hooq.demo.movie.movielist.data;

import android.content.Context;

import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.common.util.DefaultSubscriber;
import com.hooq.demo.movie.common.util.UseCase;

public class MovieListUseCase extends UseCase {

    private MovieListRepository repository;

    public MovieListUseCase(Context context) {
        repository = new MovieListRepository(context);
    }

    public void loadMovies(int page, DefaultSubscriber<MovieResults> subscriber) {
        execute(repository.loadMovies(page), subscriber);
    }
}
