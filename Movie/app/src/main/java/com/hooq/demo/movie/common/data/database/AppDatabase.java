package com.hooq.demo.movie.common.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hooq.demo.movie.common.data.dao.DateDao;
import com.hooq.demo.movie.common.data.dao.MovieDao;
import com.hooq.demo.movie.common.data.dao.MovieResultsDao;
import com.hooq.demo.movie.common.data.entity.Date;
import com.hooq.demo.movie.common.data.entity.Movie;
import com.hooq.demo.movie.common.data.entity.MovieResults;

@Database(entities = {Date.class,
        Movie.class, MovieResults.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DateDao DateDao();
    public abstract MovieDao MovieDao();
    public abstract MovieResultsDao MovieResultsDao();
}
