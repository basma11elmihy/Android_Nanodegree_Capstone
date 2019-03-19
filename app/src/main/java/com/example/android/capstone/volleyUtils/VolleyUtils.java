package com.example.android.capstone.volleyUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.capstone.MapModel.NearByFullModel;
import com.example.android.capstone.MovieDetailsActivity;
import com.example.android.capstone.moviemodel.BelongsToCollection;
import com.example.android.capstone.moviemodel.CastMain;
import com.example.android.capstone.moviemodel.MovieDetailsModel;
import com.example.android.capstone.moviemodel.MoviePosters;
import com.example.android.capstone.moviemodel.MovieVideo;
import com.example.android.capstone.moviemodel.MultiSearch;
import com.example.android.capstone.moviemodel.ReviewsResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

public class VolleyUtils {
    private List list;
    private final String TAG = "VOLLEY_ERROR";

    public void volleySimpleResults(String url, Context context, onResponce myResponce) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MultiSearch multiSearch = gson.fromJson(response, MultiSearch.class);
                list = multiSearch.getResults();
                myResponce.onSuccess(list,multiSearch);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void volleyMovieDetailsResults(String url,Context context, onResponce myResponce){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MovieDetailsModel movieModel = gson.fromJson(response, MovieDetailsModel.class);
                BelongsToCollection collection = movieModel.getBelongsToCollection();
                myResponce.onSuccess(collection,movieModel);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void volleyImageDetailsResults(String url,Context context, onResponce myResponce){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MoviePosters moviePosters = gson.fromJson(response, MoviePosters.class);
                myResponce.onSuccess(null,moviePosters);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void volleyVideoDetailsResults(String url,Context context, onResponce myResponce){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MovieVideo movieVids = gson.fromJson(response, MovieVideo.class);
                List list = movieVids.getMovieVideoResults();
                myResponce.onSuccess(list,movieVids);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void volleyCastDetailsResults(String url,Context context, onResponce myResponce){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                CastMain castMain = gson.fromJson(response, CastMain.class);
                List list = castMain.getCast();
                myResponce.onSuccess(list,castMain);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void volleyReviewsDetailsResults(String url,Context context, onResponce myResponce){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ReviewsResult reviewsResult = gson.fromJson(response, ReviewsResult.class);
                List list = reviewsResult.getMovieReviews();
                myResponce.onSuccess(list,reviewsResult);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void volleyMainSimpleResults(String url, Context context, onMainResponce myResponce, String type) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                MultiSearch multiSearch = gson.fromJson(response, MultiSearch.class);
                list = multiSearch.getResults();
                myResponce.onMainSuccess(list,multiSearch,type);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                myResponce.onMainFail(error.toString());
                Log.e(TAG,error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void volleyNearByResults(String url, Context context, onResponce myResponce) {
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                NearByFullModel nearByFullModel = gson.fromJson(response, NearByFullModel.class);
                list = nearByFullModel.getResults();
                myResponce.onSuccess(list,nearByFullModel);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG,error.toString());
                myResponce.onFail(error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

}



