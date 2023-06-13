package com.example.moviestowatch.tasks;

import android.os.AsyncTask;

import com.example.moviestowatch.activities.SavedMoviesActivity;
import com.example.moviestowatch.dao.MovieDAO;
import com.example.moviestowatch.database.MovieDatabase;
import com.example.moviestowatch.entities.MovieEntity;
import com.example.moviestowatch.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FetchSavedMoviesTask extends AsyncTask<Void, Void, List<MovieEntity>> {
    private SavedMoviesActivity savedMoviesActivity;
    private MovieDatabase movieDatabase;

    public FetchSavedMoviesTask(SavedMoviesActivity activity, MovieDatabase database) {
        this.savedMoviesActivity = activity;
        this.movieDatabase = database;
    }

    @Override
    protected List<MovieEntity> doInBackground(Void... voids) {
        // Access the DAO to query the saved movies from the database
        MovieDAO movieDao = movieDatabase.movieDao();
        return movieDao.getAllMovies();
    }

    @Override
    protected void onPostExecute(List<MovieEntity> movieEntities) {
        super.onPostExecute(movieEntities);

        // Convert MovieEntity objects to Movie java objects
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity movieEntity : movieEntities) {
            Movie movie = new Movie(movieEntity.getTitle(), movieEntity.getPosterUrl(), movieEntity.getImdbID(), movieEntity.getYear());
            movies.add(movie);
        }

        savedMoviesActivity.onMoviesFetched(movies);
    }
}

