package com.example.android.capstone.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.capstone.moviemodel.SearchResult;

import java.util.List;

@Dao
public interface FavDao {
    @Insert
    void insert(SearchResult searchResult);

    @Query("SELECT * FROM fav_table")
    LiveData<List<SearchResult>> getAllMovies();

    @Delete
    void deleteMovie(SearchResult favourite);

    @Query("SELECT favId FROM fav_table WHERE favId = :id")
    LiveData<String> loadMovieById(String id);
}
