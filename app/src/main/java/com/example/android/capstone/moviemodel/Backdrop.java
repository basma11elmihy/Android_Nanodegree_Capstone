
package com.example.android.capstone.moviemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Backdrop {

    @SerializedName("aspect_ratio")
    @Expose
    private double aspectRatio;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("height")
    @Expose
    private long height;
    @SerializedName("iso_639_1")
    @Expose
    private Object iso6391;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private long voteCount;
    @SerializedName("width")
    @Expose
    private long width;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Backdrop() {
    }

    /**
     * 
     * @param iso6391
     * @param height
     * @param filePath
     * @param width
     * @param voteAverage
     * @param aspectRatio
     * @param voteCount
     */
    public Backdrop(double aspectRatio, String filePath, long height, Object iso6391, double voteAverage, long voteCount, long width) {
        super();
        this.aspectRatio = aspectRatio;
        this.filePath = filePath;
        this.height = height;
        this.iso6391 = iso6391;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.width = width;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public Object getIso6391() {
        return iso6391;
    }

    public void setIso6391(Object iso6391) {
        this.iso6391 = iso6391;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

}
