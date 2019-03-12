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

import com.android.volley.toolbox.StringRequest;
import com.example.android.capstone.moviemodel.KnownFor;
import com.example.android.capstone.moviemodel.MultiSearch;
import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchActivityAdapter extends RecyclerView.Adapter<SearchActivityAdapter.SearchActivityViewHolder> {
    private Context context;
    private ArrayList<SearchResult> searchResultArrayList;


    public SearchActivityAdapter(Context context, ArrayList<SearchResult> list) {
        this.context = context;
        searchResultArrayList = list;
    }

    @NonNull
    @Override
    public SearchActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_search_card_view,viewGroup,false);

        return new SearchActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchActivityViewHolder searchActivityViewHolder, int i) {
        SearchResult result = searchResultArrayList.get(i);

        if (result.getKnownFor() != null){
            searchActivityViewHolder.title.setText(result.getName());
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getProfilePath()).into(searchActivityViewHolder.imageView);

            // subtitle known for

            ArrayList<KnownFor> knownFors = (ArrayList<KnownFor>) result.getKnownFor();
            if (knownFors.size() > 0) {
                String knownForString = knownFors.get(0).getTitle() + " (" + knownFors.get(0).getReleaseDate() + ")";
                searchActivityViewHolder.subTitle.setText(knownForString);
            }
        }
        else if (result.getOriginalName() != null){
            searchActivityViewHolder.title.setText(result.getName());
            searchActivityViewHolder.subTitle.setText(result.getFirstAirDate());
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getPosterPath()).into(searchActivityViewHolder.imageView);

        }
        else if (result.getTitle() != null){
            searchActivityViewHolder.title.setText(result.getTitle());
            searchActivityViewHolder.subTitle.setText(result.getReleaseDate());
            Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+result.getPosterPath()).into(searchActivityViewHolder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        if (searchResultArrayList == null)
        return 0;
        else
            return searchResultArrayList.size();
    }
    public void update(ArrayList<SearchResult> arrayList){
        if (arrayList != null) {
            searchResultArrayList.addAll(arrayList);
            notifyDataSetChanged();
        }
    }
    public class SearchActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView title;
        private TextView subTitle;

        public SearchActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            title = itemView.findViewById(R.id.card_title);
            subTitle = itemView.findViewById(R.id.card_subtitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            SearchResult currentClick = searchResultArrayList.get(position);
            String id  = String.valueOf(currentClick.getId());
            Intent intent;
            if (currentClick.getMediaType() != null) {
                switch (currentClick.getMediaType()) {
                    case "movie":
                        intent = new Intent(context, MovieDetailsActivity.class);
                        intent.putExtra("extraID", id);
                        context.startActivity(intent);
                        break;

                }
            }else{
                intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("extraID", id);
                context.startActivity(intent);
            }
        }
    }
}
