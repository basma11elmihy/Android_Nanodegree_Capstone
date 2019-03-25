package com.example.android.capstone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.capstone.moviemodel.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Intent intent;
    private ArrayList<SearchResult> mData;
    private int[] ids;

    public GridRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public void initData(){
        if (intent.getData() != null) {
            String stringData = String.valueOf(intent.getData().getSchemeSpecificPart());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            SearchResult[] results = gson.fromJson(stringData, SearchResult[].class);
            mData = new ArrayList<>(Arrays.asList(results));
            ids = intent.getIntArrayExtra(context.getResources().getString(R.string.ids_extra));
        }

    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mData == null)
        return 0;
        else return mData.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_grid_list_item);
//        if (position == 0){
//            views.setViewVisibility(R.id.widget_text_now_playing,0);
//        }
        SearchResult current = mData.get(position);

        String name = current.getTitle();
        views.setTextViewText(R.id.widget_item_title,name);

       //Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+current.getPosterPath()).into(views,R.id.widget_poster_image,ids);
        try {
            Bitmap bitmap = Picasso.with(context).load(context.getResources().getString(R.string.images_url)+current.getPosterPath()).get();
            views.setImageViewBitmap(R.id.widget_poster_image,bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String id = String.valueOf(current.getId());
        Bundle extras = new Bundle();
        extras.putString(context.getResources().getString(R.string.extraID), id);
        extras.putString(context.getResources().getString(R.string.extraTitle),current.getTitle());
        extras.putString(context.getResources().getString(R.string.extraPath),current.getPosterPath());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_poster_image, fillInIntent);


        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



}
