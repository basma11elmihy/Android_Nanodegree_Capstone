package com.example.android.capstone;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.android.capstone.moviemodel.SearchResult;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class nowPlayingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,ArrayList<SearchResult> results,int[] appWidgetIds) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.now_playing_widget);
        Intent intent = new Intent(context,GridIntentService.class);
        intent.setAction("getMovies");
        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_movies,pendingIntent);

        if (results != null){
            Intent ListIntent = new Intent(context, GridWidgetService.class);
            String ListDumb = new Gson().toJson(results);
            ListIntent.setData(Uri.fromParts("scheme",ListDumb,null));
            ListIntent.putExtra("ids",appWidgetIds);
            views.setRemoteAdapter(R.id.widget_grid_view, ListIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
   //    update(context, appWidgetManager, appWidgetIds,null);
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.now_playing_widget);
//        Intent intent = new Intent(context,GridIntentService.class);
//        intent.setAction("getMovies");
//        PendingIntent pendingIntent = PendingIntent.getService(context,0,intent,0);
//        views.setOnClickPendingIntent(R.id.appwidget_movies,pendingIntent);
        ArrayList<SearchResult> searchResults = null;
        for (int appWidgetId : appWidgetIds) {
//            GridIntentService.inflateGrid(context);
            update(context, appWidgetManager, appWidgetIds,searchResults);

       }

    }
    public static void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, ArrayList<SearchResult> searchResults){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,searchResults,appWidgetIds);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

