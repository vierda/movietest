package com.hooq.demo.movie.detail.presentation.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hooq.demo.movie.R;
import com.hooq.demo.movie.common.util.Util;
import com.hooq.demo.movie.common.widget.AutoViewPager;
import com.hooq.demo.movie.detail.data.entity.MovieDetailEntity;
import com.hooq.demo.movie.detail.data.entity.SimilarMovieEntity;
import com.hooq.demo.movie.detail.presentation.adapter.SimilarMovieAdapter;
import com.hooq.demo.movie.detail.presentation.viewmodel.DetailMovieViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieDataView {

    public static String MOVIE_ID = "movieId";

    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_release_year)
    TextView movieReleaseYear;

    @BindView(R.id.movie_description)
    TextView movieDescription;

    @BindView(R.id.view_pager)
    AutoViewPager similarMoviePager;

    @BindView(R.id.no_similar_movie)
    TextView noSimilarMovie;

    private ProgressDialog progressDialog;
    private DetailMovieViewModel detailMovieViewModel;
    private LiveData<MovieDetailEntity> movieLiveData;
    private SimilarMovieAdapter similarMovieAdapter;
    private List<SimilarMovieEntity> similarEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        ButterKnife.bind(this);

        progressDialog = Util.getInstance().getProgressDialog(DetailMovieActivity.this);

        similarEntities = new ArrayList<>();
        similarMovieAdapter = new SimilarMovieAdapter(DetailMovieActivity.this, similarEntities, this);
        similarMoviePager.setAdapter(similarMovieAdapter);

        int movieId = getIntent().getIntExtra(MOVIE_ID, 0);
        detailMovieViewModel = ViewModelProviders.of(DetailMovieActivity.this).get(DetailMovieViewModel.class);
        detailMovieViewModel.getMovieDetail(movieId);

        progressDialog.show();
        movieLiveData = detailMovieViewModel.getMovieDetailLiveData();

        observe();

    }

    private void observe() {

        movieLiveData.observe(DetailMovieActivity.this, new Observer<MovieDetailEntity>() {

            @Override
            public void onChanged(@Nullable MovieDetailEntity entity) {
                if (entity != null) {

                    Glide.with(DetailMovieActivity.this)
                            .load(entity.getPosterPath())
                            .placeholder(R.drawable.empty)
                            .fitCenter()
                            .into(moviePoster);

                    movieTitle.setText(entity.getTitle());
                    movieReleaseYear.setText(entity.getReleaseYear());
                    movieDescription.setText(entity.getDescription());

                    if (!similarEntities.isEmpty()) similarEntities.clear();


                    if (entity.getSimilarMovies()!=null && !entity.getSimilarMovies().isEmpty()) {

                        similarEntities.addAll(entity.getSimilarMovies());

                        similarMovieAdapter.setSimilarMovieEntities(similarEntities);
                        similarMovieAdapter.notifyDataSetChanged();

                        similarMoviePager.invalidate();
                        similarMoviePager.setVisibility(View.VISIBLE);
                        noSimilarMovie.setVisibility(View.GONE);

                    } else {
                        similarMoviePager.setVisibility(View.GONE);
                        noSimilarMovie.setVisibility(View.VISIBLE);
                    }

                    if (progressDialog.isShowing()) progressDialog.dismiss();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void onSimilarMovieClick(int movieId) {
        if (!progressDialog.isShowing()) progressDialog.show();
        detailMovieViewModel.getMovieDetail(movieId);
    }
}
