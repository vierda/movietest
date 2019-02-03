package com.hooq.demo.movie.detail.data;

import com.hooq.demo.movie.MovieApplication;
import com.hooq.demo.movie.common.data.database.AppDatabase;
import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.common.network.ResourceManager;
import com.hooq.demo.movie.detail.data.entity.MovieDetailEntity;
import com.hooq.demo.movie.detail.data.entity.SimilarMovieEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class DetailMovieDatabaseRepository {

    private AppDatabase appDatabase = MovieApplication.getInstance().getDatabase();

    Observable<Movie> getMovie(final int movieId) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(final Subscriber<? super Movie> subscriber) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Movie movie = appDatabase.MovieDao().findMovieById(movieId);
                        subscriber.onNext(movie);
                        subscriber.onCompleted();
                    }
                }).start();

            }
        });
    }

    Observable<MovieDetailEntity> convertMovieToMovieDetailEntity(final Movie movie, final MovieResults similarMovies) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<MovieDetailEntity>() {
            @Override
            public void call(final Subscriber<? super MovieDetailEntity> subscriber) {

                MovieDetailEntity movieDetailEntity = new MovieDetailEntity();

                do {

                    if (movie == null) break;

                    movieDetailEntity.setPosterPath(ResourceManager.getInstance().getImage(movie.getPosterPath()));
                    movieDetailEntity.setTitle(movie.getOriginalTitle());
                    movieDetailEntity.setDescription(movie.getOverview());
                    movieDetailEntity.setReleaseYear(movie.getReleaseDate());

                    if (similarMovies == null) break;
                    List<Movie> movieList = similarMovies.getResults();

                    if (movieList == null && movieList.isEmpty()) break;

                    List<SimilarMovieEntity> similarEntities = new ArrayList<>();

                    for (Movie movie : movieList) {
                        SimilarMovieEntity similarMovieEntity = new SimilarMovieEntity();
                        similarMovieEntity.setMovieId(movie.getId());
                        similarMovieEntity.setPosterPath(ResourceManager.getInstance().getImage(movie.getPosterPath()));
                        similarEntities.add(similarMovieEntity);
                    }

                    movieDetailEntity.setSimilarMovies(similarEntities);

                } while (false);

                subscriber.onNext(movieDetailEntity);
                subscriber.onCompleted();

            }
        });
    }

    Observable<MovieResults> saveSimilarMovies(final MovieResults movieResults) {
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
