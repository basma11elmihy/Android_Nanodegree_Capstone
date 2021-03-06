package com.example.android.capstone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.moviemodel.Cast;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;

import java.util.ArrayList;

public class MovieInfoFrag extends Fragment implements onResponce {
    private String id;
    private String overView;
    private TextView overViewTv;
    private ImageView moreView;
    private boolean moreClicked = false;
    private RecyclerView recyclerView;
    private ArrayList<Cast> castlist;
    private MovieCastListAdapter adapter;
    private String url;
    private ImageView lessView;


    public MovieInfoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie_info, container, false);
        if (getArguments() != null) {
            id = getArguments().getString(getContext().getResources().getString(R.string.extraID));
            overView = getArguments().getString(getContext().getResources().getString(R.string.overview_detail));
            url = getContext().getResources().getString(R.string.movie_details_base_url)+ id +
                    getContext().getResources().getString(R.string.credits_url) +
                    getContext().getResources().getString(R.string.movie_details_api_url);
        }

        recyclerView = view.findViewById(R.id.movie_castlist_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleyCastDetailsResults(url,getContext(), this);

        overViewTv = view.findViewById(R.id.movie_info_overview);
        moreView = view.findViewById(R.id.movie_overview_more);
        lessView = view.findViewById(R.id.movie_overview_less);

        overViewTv.setText(overView);

        int maxLines = 3;
        overViewTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                overViewTv.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (overViewTv.getLineCount() > maxLines) {
                        moreView.setVisibility(View.VISIBLE);
                        overViewTv.setMaxLines(maxLines);

                    }
                return true;
            }
        });
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreView.setVisibility(View.GONE);
                lessView.setVisibility(View.VISIBLE);
                overViewTv.setMaxLines(overViewTv.getLineCount());
            }
        });
        lessView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessView.setVisibility(View.GONE);
                moreView.setVisibility(View.VISIBLE);
                overViewTv.setMaxLines(maxLines);
            }
        });


        return view;

    }

    @Override
    public void onSuccess(Object responce, Object mainResponce) {
        if (responce != null){
            castlist = (ArrayList<Cast>) responce;
            adapter = new MovieCastListAdapter(getContext(),castlist);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onFail(String error) {
        Log.e("MovieInfoFrag",error);
    }

}
