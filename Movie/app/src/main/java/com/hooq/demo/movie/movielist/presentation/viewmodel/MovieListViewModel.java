package com.hooq.demo.movie.movielist.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.common.util.DefaultSubscriber;
import com.hooq.demo.movie.movielist.data.MovieListUseCase;

public class MovieListViewModel extends AndroidViewModel {

    MovieListUseCase useCase;
    MutableLiveData<MovieResults> liveData;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        useCase = new MovieListUseCase(application);
    }


    public MutableLiveData<MovieResults> getMovieListLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }


    public void loadMovies(int page) {

        useCase.loadMovies(page,new DefaultSubscriber<MovieResults>() {

            @Override
            public void onCompleted() {
                //no-opt
            }

            @Override
            public void onError(Throwable e) {
                //TODO
            }

            @Override
            public void onNext(MovieResults movieResults) {
                liveData.postValue(movieResults);
            }
        });

    }
}

