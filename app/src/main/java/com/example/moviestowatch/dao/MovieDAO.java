package com.example.moviestowatch.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moviestowatch.entities.MovieEntity;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void insertMovie(MovieEntity movie);

    @Update
    void updateMovie(MovieEntity movie);

    @Delete
    void deleteMovie(MovieEntity movie);

    @Query("SELECT * FROM movies")
    List<MovieEntity> getAllMovies();

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Query("DELETE FROM movies WHERE imdbID = :imdbID")
    void deleteMoviesByImdbID(String imdbID);

}

