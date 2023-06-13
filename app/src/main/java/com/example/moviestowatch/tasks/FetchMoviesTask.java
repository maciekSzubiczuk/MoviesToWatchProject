package com.example.moviestowatch.tasks;

import android.os.AsyncTask;

import com.example.moviestowatch.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    private String apiUrl;
    private OnMoviesFetchedListener listener;

    public FetchMoviesTask(String apiUrl, OnMoviesFetchedListener listener) {
        this.apiUrl = apiUrl;
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        List<Movie> movies = new ArrayList<>();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String jsonResponse = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray searchArray = jsonObject.getJSONArray("Search");

            for (int i = 0; i < searchArray.length(); i++) {
                JSONObject movieObject = searchArray.getJSONObject(i);
                String title = movieObject.getString("Title");
                String poster = movieObject.getString("Poster");
                String imdbID = movieObject.getString("imdbID");
                String year = movieObject.getString("Year");
                if (!poster.equals("N/A")) {
                    movies.add(new Movie(title, poster, imdbID,year));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        listener.onMoviesFetched(movies);
    }

    public interface OnMoviesFetchedListener {
        void onMoviesFetched(List<Movie> movies);
    }
}
