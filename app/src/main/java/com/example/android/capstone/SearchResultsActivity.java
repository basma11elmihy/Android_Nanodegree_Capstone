package com.example.android.capstone;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
    private static int pageNumber=1;
    private static int totalPages;
    private String newUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_search_results);
   //     setContentView(R.layout.activity_search_results);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Slide(Gravity.BOTTOM));
            getWindow().setAllowEnterTransitionOverlap(true);
        }
        url = getIntent().getStringExtra(getResources().getString(R.string.url_extra));
        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleySimpleResults(url, this, this);
        recyclerView = findViewById(R.id.search_cardView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (url.contains("popular"))
        getSupportActionBar().setTitle("Popular Movies");
        else if (url.contains("top_rated"))
            getSupportActionBar().setTitle("Top Rated Movies");
        else if (url.contains("multi"))
            getSupportActionBar().setTitle("Results");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

//                if (!recyclerView.canScrollVertically(1) && pageNumber<totalPagesc) {
//
//                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0 &&pageNumber<totalPages)
                {

                    int visibleItemCount = layoutManager .getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if((visibleItemCount+pastVisibleItems)>=totalItemCount) {
                        pageNumber = pageNumber +1;
                        newUrl = url + "&page=" + pageNumber;
                        volleyUtils.volleySimpleResults(newUrl, SearchResultsActivity.this, SearchResultsActivity.this);
                        //String number = String.valueOf(pageNumber);
                        //Toast.makeText(SearchResultsActivity.this, number, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                pageNumber = 1;
                NavUtils.navigateUpFromSameTask(this);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        pageNumber = 1;
        NavUtils.navigateUpFromSameTask(this);
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
            //pageNumber = multiSearchMain.getPage();
            if (pageNumber < totalPages) {
                if (pageNumber >= 2) {
                    adapter.update(list);
                }
                else{
                    adapter = new SearchActivityAdapter(this,list,1);
                    recyclerView.setAdapter(adapter);
            }

            }

        }
    }

    @Override
    public void onFail(String error) {
        Log.e("SearchResultsActivity",error);
    }
}
