package com.example.android.capstone;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.capstone.moviemodel.PopularTopRatedMovie;
import com.example.android.capstone.moviemodel.ResultPopularTopRated;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends CustomAppCompat {
    private static String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=704309e018dc5823efbc0ca4966083d1&language=en-US&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setLayout(R.layout.activity_main);

    }
}
