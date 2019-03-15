
package com.example.android.capstone.moviemodel;

import java.util.List;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "fav_table")
public class SearchResult implements Parcelable
{

    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("id")
    @Expose
    @Ignore
    private int id;
    @PrimaryKey
    @ColumnInfo(name = "favId")
    @NonNull
    private String favId;
    @SerializedName("profile_path")
    @Expose
    @Ignore
    private Object profilePath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("known_for")
    @Expose
    @Ignore
    private List<KnownFor> knownFor;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genre_ids")
    @Expose
    @Ignore
    private List<Integer> genreIds = null;
    @SerializedName("original_language")
    @Expose
    @Ignore
    private String originalLanguage;
    @SerializedName("backdrop_path")
    @Expose
    @Ignore
    private Object backdropPath;
    @SerializedName("overview")
    @Expose
    @Ignore
    private String overview;
    @SerializedName("origin_country")
    @Expose
    @Ignore
    private List<String> originCountry = null;
    @SerializedName("video")
    @Expose
    private boolean video;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @Ignore
    public final static Creator<SearchResult> CREATOR = new Creator<SearchResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        public SearchResult[] newArray(int size) {
            return (new SearchResult[size]);
        }

    }
    ;

    @Ignore
    protected SearchResult(Parcel in) {
        this.popularity = ((double) in.readValue((double.class.getClassLoader())));
        this.mediaType = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.profilePath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(this.knownFor, (KnownFor.class.getClassLoader()));
        if (this.originalName != null)
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
        this.voteAverage = ((double) in.readValue((double.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genreIds, (Integer.class.getClassLoader()));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.originCountry, (String.class.getClassLoader()));
        this.video = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
    }


    /**
     * 
     * @param genreIds
     * @param profilePath
     * @param originalName
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param mediaType
     * @param id
     * @param title
     * @param releaseDate
     * @param originCountry
     * @param overview
     * @param knownFor
     * @param name
     * @param posterPath
     * @param originalTitle
     * @param firstAirDate
     * @param voteAverage
     * @param video
     * @param popularity
     */
    @Ignore
    public SearchResult(double popularity, String mediaType, int id, Object profilePath, String name, List<KnownFor> knownFor, boolean adult, String originalName, int voteCount, double voteAverage, String posterPath, String firstAirDate, List<Integer> genreIds, String originalLanguage, Object backdropPath, String overview, List<String> originCountry, boolean video, String title, String originalTitle, String releaseDate) {
        super();
        this.popularity = popularity;
        this.mediaType = mediaType;
        this.id = id;
        this.profilePath = profilePath;
        this.name = name;
        this.knownFor = knownFor;
        this.originalName = originalName;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.firstAirDate = firstAirDate;
        this.genreIds = genreIds;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.originCountry = originCountry;
        this.video = video;
        this.title = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.favId = String.valueOf(id);
    }
    public SearchResult(@NonNull String favId, String posterPath, String title){
        this.favId = favId;
        this.posterPath = posterPath;
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public SearchResult withPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public SearchResult withMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SearchResult withId(int id) {
        this.id = id;
        return this;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    public SearchResult withProfilePath(Object profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchResult withName(String name) {
        this.name = name;
        return this;
    }

    public List<KnownFor> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<KnownFor> knownFor) {
        this.knownFor = knownFor;
    }

    public SearchResult withKnownFor(List<KnownFor> knownFor) {
        this.knownFor = knownFor;
        return this;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public SearchResult withOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public SearchResult withVoteCount(int voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public SearchResult withVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public SearchResult withPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public SearchResult withFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
        return this;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public SearchResult withGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
        return this;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public SearchResult withOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    public Object getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(Object backdropPath) {
        this.backdropPath = backdropPath;
    }

    public SearchResult withBackdropPath(Object backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public SearchResult withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public SearchResult withOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
        return this;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public SearchResult withVideo(boolean video) {
        this.video = video;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SearchResult withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public SearchResult withOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public SearchResult withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(popularity);
        dest.writeValue(mediaType);
        dest.writeValue(id);
        dest.writeValue(profilePath);
        dest.writeValue(name);
        dest.writeList(knownFor);
        dest.writeValue(originalName);
        dest.writeValue(voteCount);
        dest.writeValue(voteAverage);
        dest.writeValue(posterPath);
        dest.writeValue(firstAirDate);
        dest.writeList(genreIds);
        dest.writeValue(originalLanguage);
        dest.writeValue(backdropPath);
        dest.writeValue(overview);
        dest.writeList(originCountry);
        dest.writeValue(video);
        dest.writeValue(title);
        dest.writeValue(originalTitle);
        dest.writeValue(releaseDate);
    }
    @Ignore
    public int describeContents() {
        return  0;
    }

    @NonNull
    public String getFavId() {
        return favId;
    }

    public void setFavId(@NonNull String favId) {
        this.favId = favId;
    }
}
