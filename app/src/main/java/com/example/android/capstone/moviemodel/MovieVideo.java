
package com.example.android.capstone.moviemodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVideo {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("results")
    @Expose
    private List<MovieVideoResult> movieVideoResults = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MovieVideoResult> getMovieVideoResults() {
        return movieVideoResults;
    }

    public void setMovieVideoResults(List<MovieVideoResult> movieVideoResults) {
        this.movieVideoResults = movieVideoResults;
    }

}
