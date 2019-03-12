package com.example.android.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.moviemodel.SearchResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SearchResult> results;
    private int LayoutResource;

    public MainMenuAdapter(Context context, ArrayList<SearchResult> results, int layoutResource) {
        this.context = context;
        this.results = results;
        this.LayoutResource = layoutResource;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(LayoutResource,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SearchResult result = results.get(i);
        viewHolder.movieName.setText(result.getTitle());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getPosterPath()).into(viewHolder.movieImage);

    }

    @Override
    public int getItemCount() {
         return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView movieImage;
        private TextView movieName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_popular_image);
            movieName = itemView.findViewById(R.id.movie_popular_text);
        }
    }
}
