package com.hooq.demo.movie;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.hooq.demo.movie.common.data.database.AppDatabase;

public class MovieApplication extends Application {

    public static MovieApplication _instance;
    private static final String DATABASE_NAME = "MovieDb";
    private AppDatabase database;

    public static MovieApplication getInstance() {
        return _instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;

        if (database == null) {
            database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }

    }

    public AppDatabase getDatabase () {
        return database;
    }
}
