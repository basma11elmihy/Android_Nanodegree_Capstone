package com.example.android.capstone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.moviemodel.MovieVideoResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieVideoResult> results;
    private MovieVideoResult movieVideoResult;

    public MovieVideoAdapter(Context context, ArrayList<MovieVideoResult> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_video,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        movieVideoResult = results.get(i);
        if (movieVideoResult.getSite().equals("YouTube")) {
            String videoId = movieVideoResult.getKey();
            String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg";
            Picasso.with(context)
                    .load(img_url)
                    .into(viewHolder.thumbnail);
            viewHolder.title.setText(movieVideoResult.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (results != null)
        return results.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView thumbnail;
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.movie_video_thumbnail);
            title = itemView.findViewById(R.id.movie_video_title_detail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MovieVideoResult currentClick = results.get(position);
            String videoId = currentClick.getKey();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
            intent.putExtra("VIDEO_ID", videoId);
            context.startActivity(intent);
        }
    }
}
