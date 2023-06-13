package com.example.moviestowatch.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.moviestowatch.activities.SavedMoviesActivity;
import com.example.moviestowatch.dao.MovieDAO;
import com.example.moviestowatch.database.MovieDatabase;


public class DeleteMoviesByImdbIDTask extends AsyncTask<String, Void, Void> {

    private SavedMoviesActivity activity;
    private MovieDatabase movieDatabase;

    public DeleteMoviesByImdbIDTask(SavedMoviesActivity activity, MovieDatabase movieDatabase) {
        this.activity = activity;
        this.movieDatabase = movieDatabase;
    }

    @Override
    protected Void doInBackground(String... imdbIDs) {
        if (imdbIDs.length > 0) {
            String imdbID = imdbIDs[0];
            MovieDAO movieDao = movieDatabase.movieDao();
            movieDao.deleteMoviesByImdbID(imdbID);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(activity, "Movies deleted.", Toast.LENGTH_SHORT).show();

        // Fetch the updated list of saved movies from the database
        new FetchSavedMoviesTask(activity, movieDatabase).execute();
    }
}
