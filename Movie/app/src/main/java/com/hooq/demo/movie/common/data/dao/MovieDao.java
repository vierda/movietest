package com.hooq.demo.movie.common.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.hooq.demo.movie.common.data.entity.Movie;

@Dao
public interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movie... movies);

    @Query("SELECT * FROM movie where id LIKE  :movieId")
    Movie findMovieById(int movieId);
}
