package com.example.android.capstone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;

import java.util.ArrayList;


public class MovieSimilarFrag extends Fragment implements onResponce {
    private String id;
    private String url;
    private RecyclerView recyclerView;
    private SearchActivityAdapter adapter;


    public MovieSimilarFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_similar, container, false);
        if (getArguments() != null) {
            id = getArguments().getString(getContext().getResources().getString(R.string.extraID));
            url = getContext().getResources().getString(R.string.movie_details_base_url)+ id +
                    getContext().getResources().getString(R.string.similar) +
                    getContext().getResources().getString(R.string.movie_details_api_url);
        }
        recyclerView = view.findViewById(R.id.movie_similar_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleySimpleResults(url,getContext(), this);

        return view;
    }

    @Override
    public void onSuccess(Object responce, Object mainResponce) {
        if (responce != null){
            ArrayList<SearchResult> list = (ArrayList<SearchResult>) responce;
            adapter = new SearchActivityAdapter(getContext(),list);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onFail(String error) {
        Log.e("MovieSimilarFrag",error);
    }
}
