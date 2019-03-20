package com.example.android.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onMainResponce;

import java.util.ArrayList;

public class MainActivity extends CustomAppCompat implements onMainResponce {
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
    private BottomNavigationView bottomNavigationView;

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

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_movies:
                        Toast.makeText(MainActivity.this,"main",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_fav:
                        Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.action_locate:
                      //  Toast.makeText(MainActivity.this,"locate",Toast.LENGTH_LONG).show();
                        Intent MapIntent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(MapIntent);
                        break;
                }
                return true;
            }
        });


        VolleyUtils volleyUtils = new VolleyUtils();

        volleyUtils.volleyMainSimpleResults(url,this, this,getResources().getString(R.string.popular_type));
        volleyUtils.volleyMainSimpleResults(nowplayingUrl,this, this,getResources().getString(R.string.now_type));
        volleyUtils.volleyMainSimpleResults(topRatedUrl,this, this,getResources().getString(R.string.top_type));

        seeAllTopRated = findViewById(R.id.movie_seeall_topRated);
        seeAllPopular = findViewById(R.id.movie_seeall_popular);

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


    }

    private void goToAllResults(String url) {
        Intent intent = new Intent(MainActivity.this,SearchResultsActivity.class);
        intent.putExtra(getResources().getString(R.string.url_extra), url);
        startActivity(intent);
    }


    @Override
    public void onMainSuccess(Object responce, Object mainResponce, String type) {
        if (responce != null){
            ArrayList<SearchResult> results = (ArrayList<SearchResult>) responce;

            switch (type) {

                case "popular":
                    popularAdapter = new MainMenuAdapter(this, results, R.layout.list_item_popular,getResources().getString(R.string.popular_type));
                popularRV.setAdapter(popularAdapter);
                break;

                case "now":
                    popularAdapter = new MainMenuAdapter(this, results, R.layout.list_item_movie,getResources().getString(R.string.now_type));
                    nowPlayingRV.setAdapter(popularAdapter);
                    break;

                case "top":
                    popularAdapter = new MainMenuAdapter(this, results, R.layout.list_item_movie,getResources().getString(R.string.top_type));
                    topRatedRV.setAdapter(popularAdapter);
                    break;

            }
        }
    }

    @Override
    public void onMainFail(String error) {
        Log.e("MainActivity",error);
    }
}
