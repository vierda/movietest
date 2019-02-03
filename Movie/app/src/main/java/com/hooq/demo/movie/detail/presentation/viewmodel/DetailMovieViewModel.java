package com.hooq.demo.movie.detail.presentation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.hooq.demo.movie.common.util.DefaultSubscriber;
import com.hooq.demo.movie.detail.data.DetailMovieUseCase;
import com.hooq.demo.movie.detail.data.entity.MovieDetailEntity;

public class DetailMovieViewModel extends AndroidViewModel {

    DetailMovieUseCase useCase;
    MutableLiveData<MovieDetailEntity> liveData;

    public DetailMovieViewModel(@NonNull Application application) {
        super(application);
        useCase = new DetailMovieUseCase(application);
    }


    public MutableLiveData<MovieDetailEntity> getMovieDetailLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public void getMovieDetail(int movieId) {

        useCase.getMovieDetail(movieId, new DefaultSubscriber<MovieDetailEntity>() {

            @Override
            public void onCompleted() {
                //no-opt
            }

            @Override
            public void onError(Throwable e) {
                //no-opt
            }

            @Override
            public void onNext(MovieDetailEntity movieDetailEntity) {
                liveData.postValue(movieDetailEntity);
            }
        });

    }
}
