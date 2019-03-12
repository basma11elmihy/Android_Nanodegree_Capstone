package com.example.android.capstone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.moviemodel.Cast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieCastListAdapter extends RecyclerView.Adapter<MovieCastListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Cast> castList;
    private Cast castItem;

    public MovieCastListAdapter(Context context, ArrayList<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_cast_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        castItem = castList.get(i);
        String name = castItem.getName();
        String image = (String) castItem.getProfilePath();
        viewHolder.nameView.setText(name);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500"+image)
                .into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        if (castList!= null)
        return castList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nameView;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.cast_item_name);
            imageView = itemView.findViewById(R.id.cast_item_pic);
        }
    }
}
