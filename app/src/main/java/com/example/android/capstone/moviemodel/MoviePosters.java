
package com.example.android.capstone.moviemodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviePosters {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("backdrops")
    @Expose
    private List<Backdrop> backdrops = null;
    @SerializedName("posters")
    @Expose
    private List<Poster> posters = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MoviePosters() {
    }

    /**
     * 
     * @param id
     * @param backdrops
     * @param posters
     */
    public MoviePosters(long id, List<Backdrop> backdrops, List<Poster> posters) {
        super();
        this.id = id;
        this.backdrops = backdrops;
        this.posters = posters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

}
