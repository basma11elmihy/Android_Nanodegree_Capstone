package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.capstone.Database.FavViewModel;
import com.example.android.capstone.moviemodel.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment {
    private FavViewModel viewModel;
    private RecyclerView recyclerView;
    private MainMenuAdapter adapter;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.fav_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),calculateNoOfColumns(getContext())));
        viewModel = ViewModelProviders.of(this).get(FavViewModel.class);
        viewModel.getAllMovies().observe(this, new Observer<List<SearchResult>>() {
            @Override
            public void onChanged(@Nullable List<SearchResult> favourites) {
                adapter = new MainMenuAdapter(getContext(), (ArrayList<SearchResult>)
                        favourites,R.layout.list_item_movie,getResources().getString(R.string.fav_type));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
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
