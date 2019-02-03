package com.hooq.demo.movie.detail.data.entity;

public class SimilarMovieEntity {

    private String posterPath;
    private int movieId;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
