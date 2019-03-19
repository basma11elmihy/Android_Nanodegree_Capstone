package com.example.android.capstone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.capstone.moviemodel.MultiSearch;
import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;

import java.util.ArrayList;

public class SearchResultsActivity extends CustomAppCompat implements onResponce {
    private RecyclerView recyclerView;
    private ArrayList<SearchResult> list;
    private MultiSearch multiSearchMain;
    private SearchActivityAdapter adapter;
    private String url;
    private static int pageNumber = 2;
    private static int totalPages;
    private String newUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setLayout(R.layout.activity_search_results);
        setContentView(R.layout.activity_search_results);
        url = getIntent().getStringExtra(getResources().getString(R.string.url_extra));
        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleySimpleResults(url, this, this);
        recyclerView = findViewById(R.id.search_cardView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//find another way
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && pageNumber<totalPages) {
//                    Toast.makeText(SearchResultsActivity.this, "Last", Toast.LENGTH_LONG).show();
                    //show loading
                    newUrl = url+"&page="+pageNumber;
                    volleyUtils.volleySimpleResults(newUrl, SearchResultsActivity.this, SearchResultsActivity.this);
                    String number = String.valueOf(pageNumber);
                    Toast.makeText(SearchResultsActivity.this, number, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onSuccess(Object responce, Object multiSearch) {
        multiSearchMain = (MultiSearch) multiSearch;
        totalPages = multiSearchMain.getTotalPages();
        if (responce != null) {
            try {
                list = (ArrayList<SearchResult>) responce;
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
            if (multiSearchMain.getPage() >= 2) {
                adapter.update(list);
                if (pageNumber < totalPages) {
                    pageNumber++;
                }
            }
            else{
                adapter = new SearchActivityAdapter(this,list);
                recyclerView.setAdapter(adapter);
            }

        }
    }

    @Override
    public void onFail(String error) {
        Log.e("SearchResultsActivity",error);
    }
}
