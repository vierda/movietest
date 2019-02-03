package com.hooq.demo.movie.common.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.hooq.demo.movie.common.data.entity.MovieResults;

@Dao
public interface MovieResultsDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieResults... movieResults);
}
