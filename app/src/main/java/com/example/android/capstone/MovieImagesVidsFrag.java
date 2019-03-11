package com.example.android.capstone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.capstone.moviemodel.Backdrop;
import com.example.android.capstone.moviemodel.MoviePosters;
import com.example.android.capstone.moviemodel.MovieVideoResult;
import com.example.android.capstone.moviemodel.Poster;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;

import java.util.ArrayList;


public class MovieImagesVidsFrag extends Fragment implements onResponce {
    private String id;
    private String url;
    private String VideoUrl;
    private MoviePosters moviePosters;
    private ArrayList<Poster> posters;
    private ArrayList<Backdrop> backdrops;
    private RecyclerView recyclerView;
    private RecyclerView vidRecyclerView;
    private MovieImageDetailAdapter adapter;
    private ArrayList<MovieVideoResult> videoResults;
    private MovieVideoAdapter videoAdapter;


    public MovieImagesVidsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_images_vids, container, false);
        if (getArguments() != null) {
            id = getArguments().getString("extraID");
            url = getContext().getResources().getString(R.string.movie_details_base_url)+id +
                    getContext().getResources().getString(R.string.image_url) +
                    getContext().getResources().getString(R.string.movie_details_api_url);
            VideoUrl = getContext().getResources().getString(R.string.movie_details_base_url)+id +
                    getContext().getResources().getString(R.string.video_url) +
                    getContext().getResources().getString(R.string.movie_details_api_url);



        }
        recyclerView = rootView.findViewById(R.id.frag_movie_image_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleyImageDetailsResults(url,getContext(), this);

        vidRecyclerView = rootView.findViewById(R.id.frag_movie_video_rv);
        LinearLayoutManager vidLayoutManger = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        vidRecyclerView.setLayoutManager(vidLayoutManger);
        volleyUtils.volleyVideoDetailsResults(VideoUrl,getContext(), this);



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onSuccess(Object responce, Object mainResponce) {
        if (mainResponce instanceof  MoviePosters){
            moviePosters = (MoviePosters) mainResponce;
            backdrops = (ArrayList<Backdrop>) moviePosters.getBackdrops();
            adapter = new MovieImageDetailAdapter(getContext(),backdrops);
            recyclerView.setAdapter(adapter);


        }
        else if (responce != null){
            videoResults = (ArrayList<MovieVideoResult>) responce;
            ArrayList<MovieVideoResult> youTubeVids = new ArrayList<>();
            for (int i = 0 ; i <videoResults.size();i++){
                if (videoResults.get(i).getSite().equals("YouTube")){
                    youTubeVids.add(videoResults.get(i));
                }
            }
            videoAdapter = new MovieVideoAdapter(getContext(),youTubeVids);
            vidRecyclerView.setAdapter(videoAdapter);

        }
    }

    @Override
    public void onFail(String error) {

    }
}
