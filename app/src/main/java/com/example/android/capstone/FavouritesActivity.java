package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.android.capstone.Database.FavViewModel;
import com.example.android.capstone.moviemodel.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends CustomAppCompat {
    private FavViewModel viewModel;
    private RecyclerView recyclerView;
    private MainMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_favourites);
        recyclerView = findViewById(R.id.fav_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this,calculateNoOfColumns(this)));
        viewModel = ViewModelProviders.of(this).get(FavViewModel.class);
        viewModel.getAllMovies().observe(this, new Observer<List<SearchResult>>() {
            @Override
            public void onChanged(@Nullable List<SearchResult> favourites) {
                adapter = new MainMenuAdapter(FavouritesActivity.this, (ArrayList<SearchResult>)
                        favourites,R.layout.list_item_movie,getResources().getString(R.string.fav_type));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
