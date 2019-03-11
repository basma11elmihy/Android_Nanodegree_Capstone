package com.example.android.capstone;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.moviemodel.BelongsToCollection;
import com.example.android.capstone.moviemodel.Genre;
import com.example.android.capstone.moviemodel.MovieDetailsModel;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity implements onResponce {
    private ImageView mCoverPhoto;
    private ImageView mMainPhoto;
    private BelongsToCollection collection;
    private MovieDetailsModel model;
    private CollapsingToolbarLayout toolbarLayout;
    private TextView movieTitleTv;
    private TextView runTimeTv;
    private TextView genresTv;
    private TextView yearTv;
    private TextView rateTv;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String id = getIntent().getStringExtra("extraID");
        String url = getResources().getString(R.string.movie_details_base_url)+ id +
                getResources().getString(R.string.movie_details_api_url);
        VolleyUtils volleyUtils = new VolleyUtils();
        volleyUtils.volleyMovieDetailsResults(url, this, this);

        mCoverPhoto = findViewById(R.id.cover_photo);
        mMainPhoto = findViewById(R.id.main_photo);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        movieTitleTv = findViewById(R.id.movie_title);
        runTimeTv = findViewById(R.id.movie_runtime);
        genresTv = findViewById(R.id.genres);
        yearTv = findViewById(R.id.movie_year);
        rateTv = findViewById(R.id.movie_rate);
        viewPager = findViewById(R.id.movie_pager);
        tabLayout = findViewById(R.id.tab_layout);
//        nestedScrollView = findViewById(R.id.nested_scrollview);
//
//        nestedScrollView.setNestedScrollingEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putString("extraID",id);


        MovieViewPagerAdapter viewPagerAdapter = new MovieViewPagerAdapter(getSupportFragmentManager());

         viewPagerAdapter.addFragment(new MovieInfoFrag(),"Info");
         MovieImagesVidsFrag imagesVidsFrag = new MovieImagesVidsFrag();
         imagesVidsFrag.setArguments(bundle);
         viewPagerAdapter.addFragment(imagesVidsFrag,"Images & Videos");
         viewPagerAdapter.addFragment(new MovieReviewsFrag(),"Reviews");
         viewPagerAdapter.addFragment(new MovieSimilarFrag(),"Similar Movies");
         viewPagerAdapter.addFragment(new MovieRecomFrag(),"Recommendations");
         viewPager.setAdapter(viewPagerAdapter);
         tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onSuccess(Object responce, Object mainResponce) {
        if (mainResponce != null) {
            collection = (BelongsToCollection) responce;
            model = (MovieDetailsModel) mainResponce;
            ArrayList<Genre> genres = (ArrayList<Genre>) model.getGenres();
            String coverPath = null;
            String mainPath = null;
            if (collection != null) {
                coverPath = collection.getBackdropPath();
                mainPath = collection.getPosterPath();
            }
            String coverPathtwo = model.getBackdropPath();
            if (coverPathtwo != null) {
                Picasso.with(MovieDetailsActivity.this).load("https://image.tmdb.org/t/p/w500" + coverPathtwo).into(mCoverPhoto);
            }
            else {
                if (coverPath != null)
                Picasso.with(MovieDetailsActivity.this).load("https://image.tmdb.org/t/p/w500" + coverPath).into(mCoverPhoto);
            }


            String posterPathtwo = model.getPosterPath();
            if (posterPathtwo != null) {
                Picasso.with(MovieDetailsActivity.this).load("https://image.tmdb.org/t/p/w500" + posterPathtwo).into(mMainPhoto);
            }
            else{
                if (mainPath != null)
                Picasso.with(MovieDetailsActivity.this).load("https://image.tmdb.org/t/p/w500" + mainPath).into(mMainPhoto);
            }

            String movieTitle = model.getTitle();
            String runTime = String.valueOf(model.getRuntime());
            String year = model.getReleaseDate();
            String rate = String.valueOf(model.getVoteAverage());

            toolbarLayout.setTitle(movieTitle);
            movieTitleTv.setText(movieTitle);
            runTimeTv.setText(runTime + " Min");
            yearTv.setText(year);
            rateTv.setText(rate);

            Genre genre;
            String genreText;

            for (int i = 0 ; i< genres.size(); i++){
                genre = genres.get(i);
                genreText = genre.getName();
                if (i == genres.size() -1){
                    genresTv.append(genreText);
                }
                else {
                    genresTv.append(genreText + " / ");
                }

            }

        }
    }

    @Override
    public void onFail(String error) {

    }
}
