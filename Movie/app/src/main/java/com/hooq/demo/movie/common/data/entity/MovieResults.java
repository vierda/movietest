package com.hooq.demo.movie.common.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.hooq.demo.movie.common.data.converter.DateConverter;
import com.hooq.demo.movie.common.data.converter.MovieConverter;

import java.util.List;

@Entity
public class MovieResults {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "results")
    @TypeConverters(MovieConverter.class)
    private List<Movie> results;

    @ColumnInfo(name = "page")
    private int page;

    @SerializedName("total_results")
    @ColumnInfo(name = "total_results")
    private int totalResults;

    @SerializedName("total_pages")
    @ColumnInfo(name = "total_pages")
    private int totalPages;

    @ColumnInfo(name = "dates")
    @TypeConverters(DateConverter.class)
    private Date dates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }
}
