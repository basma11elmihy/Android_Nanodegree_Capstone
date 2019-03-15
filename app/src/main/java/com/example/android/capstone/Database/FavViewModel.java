package com.example.android.capstone.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.capstone.moviemodel.SearchResult;

import java.util.List;

public class FavViewModel extends AndroidViewModel {
    private FavRepository mRepository;
    private LiveData<List<SearchResult>> mAllMovies;

    public FavViewModel(Application application){
        super(application);
        mRepository = new FavRepository(application);
        mAllMovies = mRepository.getmAlMovies();
    }

    public LiveData<List<SearchResult>> getAllMovies() {
        return mAllMovies;
    }


    public void insert(SearchResult favourite){
        mRepository.insert(favourite);
    }
    public void delete(SearchResult favourite){
        mRepository.delete(favourite);
    }
}
