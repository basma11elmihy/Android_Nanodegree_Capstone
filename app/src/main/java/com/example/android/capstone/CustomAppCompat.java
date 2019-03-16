package com.example.android.capstone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.capstone.moviemodel.MultiSearch;
import com.example.android.capstone.moviemodel.ResultPopularTopRated;
import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;
import com.ferfalk.simplesearchview.SimpleSearchView;

import java.util.ArrayList;

public class CustomAppCompat extends AppCompatActivity implements onResponce {
    Toolbar toolbar;
    SimpleSearchView searchView;
    private String SEARCH_URL = "https://api.themoviedb.org/3/search/multi?api_key=704309e018dc5823efbc0ca4966083d1&query=";
    ArrayList<SearchResult> list;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    protected void setLayout(@LayoutRes int layout)
    {
        setContentView(layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.search_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CustomAppCompat.this);
        recyclerView.setLayoutManager(layoutManager);
        searchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                recyclerView.setAdapter(null);
                recyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewShownAnimation() {

            }

            @Override
            public void onSearchViewClosedAnimation() {

            }
        });

        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
               //send url w 5las :D
                Intent intent = new Intent(CustomAppCompat.this,SearchResultsActivity.class);
                intent.putExtra("url",SEARCH_URL+query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);

                if(!newText.equals("")) {
                    String url = SEARCH_URL + newText;
                    VolleyUtils volleyUtils = new VolleyUtils();
                    volleyUtils.volleySimpleResults(url, CustomAppCompat.this, CustomAppCompat.this);
                }
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onSuccess(Object responce, Object multiSearch) {
        if (responce != null) {
            list = (ArrayList<SearchResult>) responce;
            searchAdapter = new SearchAdapter(CustomAppCompat.this,list);
            recyclerView.setAdapter(searchAdapter);
        }

    }

    @Override
    public void onFail(String error) {

    }
}
