package com.example.android.capstone;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;

import java.util.ArrayList;

public class GridIntentService extends IntentService implements onResponce {
    private String nowPlayingUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=704309e018dc5823efbc0ca4966083d1";
    private ArrayList<SearchResult> mData;
    private static Context con;
    private SearchResult result;


    public GridIntentService() {
        super("GridIntentService");
    }


    public static void inflateGrid(Context context) {
        Intent intent = new Intent(context, GridIntentService.class);
        con = context;
        intent.setAction("getMovies");
        context.startActivity(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals("getMovies")) {
                getGridData();
            }
        }
    }

    private void getGridData() {
        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleySimpleResults(nowPlayingUrl, this, this);
    }

    @Override
    public void onSuccess(Object responce, Object mainResponce) {
        mData = (ArrayList<SearchResult>) responce;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), nowPlayingWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        nowPlayingWidget.update(getApplicationContext(),appWidgetManager,appWidgetIds,mData);

    }

    @Override
    public void onFail(String error) {

    }
}


