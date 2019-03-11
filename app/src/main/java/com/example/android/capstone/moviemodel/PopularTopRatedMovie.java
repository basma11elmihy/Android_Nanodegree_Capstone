
package com.example.android.capstone.moviemodel;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularTopRatedMovie implements Parcelable
{

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<ResultPopularTopRated> results = null;
    public final static Creator<PopularTopRatedMovie> CREATOR = new Creator<PopularTopRatedMovie>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PopularTopRatedMovie createFromParcel(Parcel in) {
            return new PopularTopRatedMovie(in);
        }

        public PopularTopRatedMovie[] newArray(int size) {
            return (new PopularTopRatedMovie[size]);
        }

    }
    ;

    protected PopularTopRatedMovie(Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (ResultPopularTopRated.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public PopularTopRatedMovie() {
    }

    /**
     * 
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public PopularTopRatedMovie(int page, int totalResults, int totalPages, List<ResultPopularTopRated> results) {
        super();
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PopularTopRatedMovie withPage(int page) {
        this.page = page;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public PopularTopRatedMovie withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PopularTopRatedMovie withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public List<ResultPopularTopRated> getResults() {
        return results;
    }

    public void setResults(List<ResultPopularTopRated> results) {
        this.results = results;
    }

    public PopularTopRatedMovie withResults(List<ResultPopularTopRated> resultPopularTopRateds) {
        this.results = resultPopularTopRateds;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
