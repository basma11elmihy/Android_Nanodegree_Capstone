package com.example.android.capstone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.capstone.moviemodel.KnownFor;
import com.example.android.capstone.moviemodel.SearchResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private ArrayList<SearchResult> results;
    private SearchResult result;

    public SearchAdapter(Context context, ArrayList<SearchResult> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_search,viewGroup,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
         result = results.get(i);
        if (result.getKnownFor() != null){
            searchViewHolder.title.setText(result.getName());
            Picasso.with(context).load(context.getResources().getString(R.string.images_url)+result.getProfilePath()).into(searchViewHolder.imageView);
            // subtitle known for
            ArrayList<KnownFor> knownFors = (ArrayList<KnownFor>) result.getKnownFor();
            if (knownFors.size() > 0) {
                String knownForString = knownFors.get(0).getTitle() + " (" + knownFors.get(0).getReleaseDate() + ")";
                searchViewHolder.subTitle.setText(knownForString);
            }
        }
        else if (result.getOriginalName() != null){
            searchViewHolder.title.setText(result.getName());
            searchViewHolder.subTitle.setText(result.getFirstAirDate());
            Picasso.with(context).load(context.getResources().getString(R.string.images_url)+result.getPosterPath()).into(searchViewHolder.imageView);

        }
        else if (result.getTitle() != null){
            searchViewHolder.title.setText(result.getTitle());
            searchViewHolder.subTitle.setText(result.getReleaseDate());
            Picasso.with(context).load(context.getResources().getString(R.string.images_url)+result.getPosterPath()).into(searchViewHolder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        if (results == null)
        return 0;
        else if (results.size() >= 5)
            return 5;
        else if (results.size() >= 1)
            return results.size();
        else
            return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView title;
        private TextView subTitle;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_image);
            title = itemView.findViewById(R.id.search_title);
            subTitle = itemView.findViewById(R.id.search_SubTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            SearchResult currentClick = results.get(position);
            String id  = String.valueOf(currentClick.getId());
            Intent intent;
            switch (currentClick.getMediaType()){
                case "movie":
                    intent = new Intent(context,MovieDetailsActivity.class);
                    intent.putExtra(context.getResources().getString(R.string.extraID),id);
                    context.startActivity(intent);
                    break;

            }
        }
    }
}
