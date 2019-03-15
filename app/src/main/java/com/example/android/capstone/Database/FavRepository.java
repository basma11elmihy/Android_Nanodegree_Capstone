package com.example.android.capstone.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.capstone.moviemodel.SearchResult;

import java.util.List;

public class FavRepository {
    private FavDao favouriteDao;
    private LiveData<List<SearchResult>> mAlMovies;

    FavRepository(Application application){
        FavRoomDatabase db = FavRoomDatabase.getDatabase(application);
        favouriteDao = db.favouriteDao();
        mAlMovies = favouriteDao.getAllMovies();
    }
    public LiveData<List<SearchResult>> getmAlMovies(){
        return mAlMovies;
    }

    public void insert(SearchResult favourite){
        new insertAsyncTask(favouriteDao).execute(favourite);
    }
    public void delete(SearchResult favourite){
        new DeleteAsyncTask(favouriteDao).execute(favourite);
    }


    private static class insertAsyncTask extends AsyncTask<SearchResult,Void,Void> {
        private FavDao mAsyncFavouriteDao;

        insertAsyncTask(FavDao dao){
            mAsyncFavouriteDao = dao;
        }

        @Override
        protected Void doInBackground(SearchResult... favourites) {
            mAsyncFavouriteDao.insert(favourites[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<SearchResult,Void,Void>{
        private FavDao mAsyncFavouriteDao;

        DeleteAsyncTask(FavDao dao){
            mAsyncFavouriteDao = dao;
        }

        @Override
        protected Void doInBackground(SearchResult... favourites) {
            mAsyncFavouriteDao.deleteMovie(favourites[0]);
            return null;
        }
    }
}
