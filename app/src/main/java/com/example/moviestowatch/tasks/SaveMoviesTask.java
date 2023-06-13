package com.example.moviestowatch.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.moviestowatch.dao.MovieDAO;
import com.example.moviestowatch.database.MovieDatabase;
import com.example.moviestowatch.entities.MovieEntity;

import java.util.ArrayList;
import java.util.List;

public class SaveMoviesTask extends AsyncTask<List<MovieEntity>, Void, Void> {
    private Context context;
    private MovieDatabase movieDatabase;

    public SaveMoviesTask(Context context, MovieDatabase movieDatabase) {
        this.context = context;
        this.movieDatabase = movieDatabase;
    }

    @Override
    protected Void doInBackground(List<MovieEntity>... movieLists) {
        if (movieLists.length > 0) {
            List<MovieEntity> movies = movieLists[0];
            MovieDAO movieDao = movieDatabase.movieDao();
            List<MovieEntity> allMovies = movieDao.getAllMovies();
            List<String> imdbIDs = new ArrayList<>();
            for (MovieEntity tempMovie : allMovies) {
                imdbIDs.add(tempMovie.getImdbID());
            }
            // inserting only new movies to database
            for (MovieEntity movie : movies) {
                if (!imdbIDs.contains(movie.getImdbID())) {
                    movieDao.insertMovie(movie);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Selected movies saved to your to watch list!", Toast.LENGTH_SHORT).show();
    }
}
