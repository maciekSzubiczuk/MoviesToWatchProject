package com.example.moviestowatch.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Movie{
    private String title;
    private String poster;
    private String year;
    private int id;
    private String imdbID;
    private boolean selected;
    private boolean saved;


    public Movie(String title, String poster,String imdbID) {
        this.title = title;
        this.poster = poster;
        this.imdbID = imdbID;
        this.selected = false;
        this.saved = false;
    }

    public Movie(String title, String poster,String imdbID,String year) {
        this.title = title;
        this.poster = poster;
        this.imdbID = imdbID;
        this.year = year;
        this.selected = false;
        this.saved = false;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}

