package com.example.android.capstone;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.capstone.moviemodel.MultiSearch;
import com.example.android.capstone.moviemodel.PopularTopRatedMovie;
import com.example.android.capstone.moviemodel.ResultPopularTopRated;
import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onMainResponce;
import com.example.android.capstone.volleyUtils.onResponce;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CustomAppCompat implements onMainResponce {
    private String url;
    private RecyclerView popularRV;
    private MainMenuAdapter popularAdapter;
    private String nowplayingUrl;
    private RecyclerView nowPlayingRV;
    private String topRatedUrl;
    private RecyclerView topRatedRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        url = getResources().getString(R.string.popular_url);
        nowplayingUrl = getResources().getString(R.string.nowPlaying_url);
        topRatedUrl = getResources().getString(R.string.top_rated_url);

        popularRV = findViewById(R.id.movie_popular_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        popularRV.setLayoutManager(layoutManager);

        nowPlayingRV = findViewById(R.id.movie_now_playing_rv);
        LinearLayoutManager nowlayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        nowPlayingRV.setLayoutManager(nowlayoutManager);


        topRatedRV = findViewById(R.id.movie_top_rated_rv);
        LinearLayoutManager toplayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        topRatedRV.setLayoutManager(toplayoutManager);


        VolleyUtils volleyUtils = new VolleyUtils();

        volleyUtils.volleyMainSimpleResults(url,this, this,"popular");
        volleyUtils.volleyMainSimpleResults(nowplayingUrl,this, this,"now");
        volleyUtils.volleyMainSimpleResults(topRatedUrl,this, this,"top");


    }


    @Override
    public void onMainSuccess(Object responce, Object mainResponce, String type) {
        if (responce != null){
            ArrayList<SearchResult> results = (ArrayList<SearchResult>) responce;
            popularAdapter = new MainMenuAdapter(this, results, R.layout.list_item_popuar_movie);
            switch (type) {

                case "popular":
                popularRV.setAdapter(popularAdapter);
                break;

                case "now":
                    nowPlayingRV.setAdapter(popularAdapter);
                    break;

                case "top":
                    topRatedRV.setAdapter(popularAdapter);
                    break;

            }
        }
    }

    @Override
    public void onMainFail(String error) {

    }
}
