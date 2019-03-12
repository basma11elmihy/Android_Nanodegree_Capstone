package com.example.android.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.moviemodel.MovieReviews;

import java.util.ArrayList;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieReviews> results;
    private Boolean moreClicked;

    public MovieReviewAdapter(Context context, ArrayList<MovieReviews> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_reviews_movie,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MovieReviews movieReview = results.get(i);
        TextView textView = viewHolder.body;
        ImageView imageView = viewHolder.moreView;

        viewHolder.author.setText(movieReview.getAuthor());
        viewHolder.body.setText(movieReview.getContent());
        imageView.setImageResource(R.drawable.ic_expand_more_black_24dp);

        int maxLines = 3;

        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                moreClicked = false;
                textView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (!moreClicked) {
                    if (textView.getLineCount() > maxLines) {
                        imageView.setVisibility(View.VISIBLE);
                        textView.setMaxLines(maxLines);

                    }
                    moreClicked = true;
                }
                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreClicked) {
                    imageView.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    textView.setMaxLines(textView.getLineCount());
                    moreClicked = false;
                } else {
                    imageView.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    textView.setMaxLines(maxLines);
                    moreClicked = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (results == null)
            return 0;
        else
            return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView author;
        private TextView body;
        private ImageView moreView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.movie_review_author);
            body = itemView.findViewById(R.id.movie_review_body);
            moreView = itemView.findViewById(R.id.movie_review_more);
        }
    }
}
