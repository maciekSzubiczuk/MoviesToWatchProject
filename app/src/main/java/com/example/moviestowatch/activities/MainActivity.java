package com.example.moviestowatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.moviestowatch.R;
import com.example.moviestowatch.adapters.MovieAdapter;
import com.example.moviestowatch.database.MovieDatabase;
import com.example.moviestowatch.entities.MovieEntity;
import com.example.moviestowatch.models.Movie;
import com.example.moviestowatch.tasks.FetchMoviesTask;
import com.example.moviestowatch.tasks.SaveMoviesTask;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

private RecyclerView recyclerView;
private MovieAdapter movieAdapter;
private SearchView searchView;
private String apiUrl;
private Button viewSelectedButton;
private MovieDatabase movieDatabase;

    private class MoviesFetchedListener implements FetchMoviesTask.OnMoviesFetchedListener {
        @Override
        public void onMoviesFetched(List<Movie> movies) {
            movieAdapter = new MovieAdapter(MainActivity.this, movies);
            recyclerView.setAdapter(movieAdapter);
        }
    }

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    searchView = findViewById(R.id.searchView);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if (!query.isEmpty()) {
                // Build the API URL with the user's query
                apiUrl = "https://www.omdbapi.com/?apikey=448310f1&s=" + query;
                // Make API request
                FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(apiUrl, new MoviesFetchedListener());
                fetchMoviesTask.execute();
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });

    viewSelectedButton = findViewById(R.id.viewSelectedButton);
    viewSelectedButton.setOnClickListener(view -> {
        if (movieAdapter != null) {
            List<Movie> selectedMovies = movieAdapter.getSelectedMovies();
            if (selectedMovies.isEmpty()) {
                Toast.makeText(this, "No movies selected.", Toast.LENGTH_SHORT).show();
            } else {
                // Convert selectedMovies to MovieEntity objects
                List<MovieEntity> movieEntities = new ArrayList<>();
                for (Movie movie : selectedMovies) {
                    MovieEntity movieEntity = new MovieEntity();
                    movieEntity.setTitle(movie.getTitle());
                    movieEntity.setPosterUrl(movie.getPoster());
                    movieEntity.setImdbID(movie.getImdbID());
                    movieEntity.setYear(movie.getYear());
                    movieEntities.add(movieEntity);
                }
                // Save movies to the local database
                new SaveMoviesTask(MainActivity.this, movieDatabase).execute(movieEntities);
            }
        } else {
            Toast.makeText(this, "No movies available.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(MainActivity.this, SavedMoviesActivity.class);
        startActivity(intent);
    });

    // Initialize the database instance
    movieDatabase = MovieDatabase.getInstance(this);
}

}