package com.example.android.capstone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onMainResponce;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class MoviesFragment extends Fragment implements onMainResponce {

    private String url;
    private RecyclerView popularRV;
    private MainMenuAdapter popularAdapter;
    private String nowplayingUrl;
    private RecyclerView nowPlayingRV;
    private String topRatedUrl;
    private RecyclerView topRatedRV;
    private TextView seeAllNowPlaying;
    private TextView seeAllTopRated;
    private TextView seeAllPopular;



    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        url = getResources().getString(R.string.popular_url);
        nowplayingUrl = getResources().getString(R.string.nowPlaying_url);
        topRatedUrl = getResources().getString(R.string.top_rated_url);

        popularRV = view.findViewById(R.id.movie_popular_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        popularRV.setLayoutManager(layoutManager);

        nowPlayingRV = view.findViewById(R.id.movie_now_playing_rv);
        LinearLayoutManager nowlayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        nowPlayingRV.setLayoutManager(nowlayoutManager);


        topRatedRV = view.findViewById(R.id.movie_top_rated_rv);
        LinearLayoutManager toplayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topRatedRV.setLayoutManager(toplayoutManager);

        VolleyUtils volleyUtils = new VolleyUtils();

        volleyUtils.volleyMainSimpleResults(url, getContext(),  this, getResources().getString(R.string.popular_type));
        volleyUtils.volleyMainSimpleResults(nowplayingUrl, getContext(),  this, getResources().getString(R.string.now_type));
        volleyUtils.volleyMainSimpleResults(topRatedUrl, getContext(),  this, getResources().getString(R.string.top_type));

        seeAllTopRated = view.findViewById(R.id.movie_seeall_topRated);
        seeAllPopular = view.findViewById(R.id.movie_seeall_popular);

        seeAllTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAllResults(topRatedUrl);
            }
        });

        seeAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAllResults(url);
            }
        });


        return view;
    }

        private void goToAllResults (String url){
            Intent intent = new Intent(getContext(), SearchResultsActivity.class);
            intent.putExtra(getResources().getString(R.string.url_extra), url);
            startActivity(intent);
        }


        @Override
        public void onMainSuccess (Object responce, Object mainResponce, String type){
            if (responce != null) {
                ArrayList<SearchResult> results = (ArrayList<SearchResult>) responce;

                switch (type) {

                    case "popular":
                        popularAdapter = new MainMenuAdapter(getContext(), results, R.layout.list_item_popular, getResources().getString(R.string.popular_type));
                        popularRV.setAdapter(popularAdapter);
                        break;

                    case "now":
                        popularAdapter = new MainMenuAdapter(getContext(), results, R.layout.list_item_movie, getResources().getString(R.string.now_type));
                        nowPlayingRV.setAdapter(popularAdapter);
                        break;

                    case "top":
                        popularAdapter = new MainMenuAdapter(getContext(), results, R.layout.list_item_movie, getResources().getString(R.string.top_type));
                        topRatedRV.setAdapter(popularAdapter);
                        break;

                }
            }
        }

        @Override
        public void onMainFail (String error){
            Log.e("MainActivity", error);
        }


    }


