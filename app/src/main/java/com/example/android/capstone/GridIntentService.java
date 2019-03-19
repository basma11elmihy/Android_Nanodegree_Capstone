package com.example.android.capstone;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;

import java.util.ArrayList;

public class GridIntentService extends IntentService implements onResponce {
    private String nowPlayingUrl;
    private ArrayList<SearchResult> mData;
    private final String TAG = "GridIntentService";
    public GridIntentService() {
        super("GridIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(getResources().getString(R.string.getMovies_action))) {
                getGridData();
            }
        }
    }

    private void getGridData() {
        VolleyUtils volleyUtils = new VolleyUtils();
        nowPlayingUrl = getResources().getString(R.string.nowPlaying_url);
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
        Log.e(TAG,error);
    }
}


