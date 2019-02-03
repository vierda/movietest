package com.hooq.demo.movie.detail.data.entity;

import java.util.List;

public class MovieDetailEntity {

    private String posterPath;
    private String title;
    private String releaseYear;
    private String description;
    private List<SimilarMovieEntity> similarMovies;


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SimilarMovieEntity> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(List<SimilarMovieEntity> similarMovies) {
        this.similarMovies = similarMovies;
    }
}
