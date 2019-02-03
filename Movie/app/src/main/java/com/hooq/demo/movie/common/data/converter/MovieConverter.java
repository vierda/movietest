package com.hooq.demo.movie.common.data.converter;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.util.Util;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MovieConverter {

    Gson gson = Util.getInstance().getGson();

    @TypeConverter
    public Movie getMovie(String data) {
        Type type = new TypeToken<Movie>() {}.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public String getStringFromMovie(Movie movie) {
        return gson.toJson(movie);
    }

    @TypeConverter
    public List<Movie> getListMovie(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Movie>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String getStringFromListMovie(List<Movie> movieList) {
        return gson.toJson(movieList);
    }
}
