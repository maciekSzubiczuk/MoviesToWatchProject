package com.example.moviestowatch.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestowatch.R;
import com.example.moviestowatch.entities.MovieEntity;
import com.example.moviestowatch.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;
    private List<Movie> selectedMovies = new ArrayList<>();

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        this.selectedMovies = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());
        Picasso.get().load(movie.getPoster()).into(holder.posterImageView);

        // Set selected state
        if (movie.isSelected()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selectedColor));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        // Color selected movies on click and remove color if they were already selected
        holder.itemView.setOnClickListener(view -> {
            movie.setSelected(!movie.isSelected());
            if (movie.isSelected()) {
                selectedMovies.add(movie);
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selectedColor));
            } else {
                selectedMovies.remove(movie);
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getSelectedMovies() {
        return selectedMovies;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setSelectedMovies(List<Movie> selectedMovies) {
        this.selectedMovies = selectedMovies;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView yearTextView;
        ImageView posterImageView;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

}
