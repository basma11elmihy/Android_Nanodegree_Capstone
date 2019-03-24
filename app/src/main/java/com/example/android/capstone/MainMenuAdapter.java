package com.example.android.capstone;

import android.content.Context;
import android.content.Intent;
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
    private String type;

    public MainMenuAdapter(Context context, ArrayList<SearchResult> results, int layoutResource,String type) {
        this.context = context;
        this.results = results;
        this.LayoutResource = layoutResource;
        this.type = type;
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
        Picasso.with(context).load(context.getResources().getString(R.string.images_url)+result.getPosterPath()).into(viewHolder.movieImage);
        if (LayoutResource == R.layout.list_item_popular){
            viewHolder.runTime.setText(result.getReleaseDate());
            viewHolder.rate.setText(String.valueOf(result.getVoteAverage()));
        }

    }

    @Override
    public int getItemCount() {
        if (results == null)
            return 0;
        else if (type.equals(context.getResources().getString(R.string.now_type))
                ||type.equals(context.getResources().getString(R.string.fav_type))){
            return results.size();
        }
        else if (results.size() >= 5)
            return 5;
        else if (results.size() >= 1)
            return results.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView movieImage;
        private TextView movieName;
        private TextView runTime;
        private TextView rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (LayoutResource == R.layout.list_item_popular){
            movieImage = itemView.findViewById(R.id.movie_popular_image_card);
            movieName = itemView.findViewById(R.id.movie_popular_text_card);
            runTime = itemView.findViewById(R.id.movie_popular_time);
            rate = itemView.findViewById(R.id.movie_rate_card);
            } else{
                movieImage = itemView.findViewById(R.id.movie_popular_image);
                movieName = itemView.findViewById(R.id.movie_popular_text);
            }
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            SearchResult currentClick = results.get(position);
            String id;
            if (!type.equals(context.getResources().getString(R.string.fav_type))) {
                id = String.valueOf(currentClick.getId());
            }
            else{
                id = String.valueOf(currentClick.getFavId());
            }
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(context.getResources().getString(R.string.extraID), id);
            intent.putExtra(context.getResources().getString(R.string.extraTitle),currentClick.getTitle());
            intent.putExtra(context.getResources().getString(R.string.extraPath),currentClick.getPosterPath());
            context.startActivity(intent);
        }
    }
}
