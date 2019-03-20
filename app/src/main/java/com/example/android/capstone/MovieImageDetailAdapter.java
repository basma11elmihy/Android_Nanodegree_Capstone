package com.example.android.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.capstone.moviemodel.Backdrop;
import com.example.android.capstone.moviemodel.MoviePosters;
import com.example.android.capstone.moviemodel.Poster;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//https://stackoverflow.com/questions/8841159/how-to-make-youtube-video-thumbnails-in-android
public class MovieImageDetailAdapter extends RecyclerView.Adapter<MovieImageDetailAdapter.ImageDetailViewHolder>{
    private Context context;
    private ArrayList<Backdrop> backdropsUrls;
    private Backdrop backdrop;
    private String backdropUrl;

    public MovieImageDetailAdapter(Context context, ArrayList<Backdrop> backdropsUrls) {
        this.context = context;
        this.backdropsUrls = backdropsUrls;
    }

    @NonNull
    @Override
    public ImageDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_image,viewGroup,false);
        return new ImageDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDetailViewHolder imageDetailViewHolder, int i) {
        if (backdropsUrls != null){
            backdrop = backdropsUrls.get(i);
            backdropUrl = backdrop.getFilePath();
            Picasso.with(context).load(context.getResources().getString(R.string.images_url)+backdropUrl).into(imageDetailViewHolder.mBackdropView);
        }


    }

    @Override
    public int getItemCount() {
        if (backdropsUrls== null)
            return 0;
        else
            return backdropsUrls.size();
    }

    public class ImageDetailViewHolder extends RecyclerView.ViewHolder{
        private ImageView mBackdropView;

        public ImageDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            mBackdropView = itemView.findViewById(R.id.movie_detail_backdrop);
        }
    }
}
