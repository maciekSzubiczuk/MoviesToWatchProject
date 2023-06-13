package com.example.moviestowatch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviestowatch.dao.MovieDAO;
import com.example.moviestowatch.entities.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDAO movieDao();
    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie_database_1")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

