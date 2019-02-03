package com.hooq.demo.movie.movielist.presentation.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.AbsListView;
import android.widget.GridView;

import com.hooq.demo.movie.R;
import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.common.util.Util;
import com.hooq.demo.movie.detail.presentation.view.DetailMovieActivity;
import com.hooq.demo.movie.movielist.presentation.adapter.MovieAdapter;
import com.hooq.demo.movie.movielist.presentation.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MovieListActivity extends AppCompatActivity {

    @BindView(R.id.gridview)
    GridView gridView;

    private MovieListViewModel movieListViewModel;
    private LiveData<MovieResults> movieListLiveData;
    private MovieAdapter adapter;
    private ProgressDialog progressDialog;
    private int currentPage = 0;
    private int pages = 0;
    private List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);

        ButterKnife.bind(this);

        progressDialog = Util.getInstance().getProgressDialog(MovieListActivity.this);
        adapter = new MovieAdapter(this, movies);

        DisplayMetrics displayMetrics = this.getResources()
                .getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        int columnWidth = (int) ((screenWidth / 2));
        gridView.setColumnWidth(columnWidth);
        gridView.setVerticalSpacing(2);
        gridView.setHorizontalSpacing(2);
        gridView.setAdapter(adapter);

        movieListViewModel = ViewModelProviders.of(MovieListActivity.this).get(MovieListViewModel.class);
        loadMovies();

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                currentPage = currentPage + 1;
                if (currentPage <= pages)
                    loadMovies();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //no opt
            }
        });


        movieListLiveData = movieListViewModel.getMovieListLiveData();
        movieListLiveData.observe(MovieListActivity.this, new Observer<MovieResults>() {

            @Override
            public void onChanged(@Nullable MovieResults movieResults) {

                do {
                    if (movieResults == null || movieResults.getResults() == null) break;
                    if (movieResults.getResults().isEmpty()) break;

                    pages = movieResults.getTotalPages();
                    movies.addAll(movieResults.getResults());
                    adapter.notifyDataSetChanged();

                    progressDialog.dismiss();
                } while (false);

            }
        });

    }

    private void loadMovies() {
        movieListViewModel.loadMovies(currentPage);
        progressDialog.show();

    }

    @OnItemClick(R.id.gridview)
    void onItemClick(int position) {
        Intent myIntent = new Intent(this, DetailMovieActivity.class);
        myIntent.putExtra(DetailMovieActivity.MOVIE_ID, adapter.getItem(position).getId());
        startActivity(myIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }
}
