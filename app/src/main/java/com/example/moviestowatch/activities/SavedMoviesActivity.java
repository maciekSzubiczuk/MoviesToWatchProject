package com.example.moviestowatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviestowatch.R;
import com.example.moviestowatch.adapters.MovieAdapter;
import com.example.moviestowatch.dao.MovieDAO;
import com.example.moviestowatch.database.MovieDatabase;
import com.example.moviestowatch.entities.MovieEntity;
import com.example.moviestowatch.models.Movie;
import com.example.moviestowatch.tasks.DeleteMoviesByImdbIDTask;
import com.example.moviestowatch.tasks.FetchSavedMoviesTask;

import java.util.ArrayList;
import java.util.List;

public class SavedMoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private MovieDatabase movieDatabase;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        deleteButton = findViewById(R.id.deleteButton);
        movieDatabase = MovieDatabase.getInstance(this);

        // Get the saved movies from the database
        new FetchSavedMoviesTask(this, movieDatabase).execute();

        deleteButton.setOnClickListener(view -> {
            if (movieAdapter != null) {
                List<Movie> selectedMovies = movieAdapter.getSelectedMovies();
                if (selectedMovies.isEmpty()) {
                    Toast.makeText(this, "No movies selected.", Toast.LENGTH_SHORT).show();
                } else {
                    for (Movie movie : selectedMovies) {
                        new DeleteMoviesByImdbIDTask(SavedMoviesActivity.this, movieDatabase).execute(movie.getImdbID());
                    }
                }
            } else {
                Toast.makeText(this, "No movies available.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onMoviesFetched(List<Movie> movies) {
        movieAdapter = new MovieAdapter(this, movies);
        recyclerView.setAdapter(movieAdapter);
    }

}
