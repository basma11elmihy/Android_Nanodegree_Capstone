package com.example.android.capstone;

import android.app.ActivityOptions;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.Database.FavRoomDatabase;
import com.example.android.capstone.Database.FavViewModel;
import com.example.android.capstone.moviemodel.BelongsToCollection;
import com.example.android.capstone.moviemodel.Genre;
import com.example.android.capstone.moviemodel.MovieDetailsModel;
import com.example.android.capstone.moviemodel.SearchResult;
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
    private String overView;
    private MovieViewPagerAdapter viewPagerAdapter;
    private Bundle bundle;
    private FavViewModel viewModel;
    private FavRoomDatabase database;
    private LiveData<String> roomMovieId;
    private FloatingActionButton mFab;
    private String id;
    private ImageView backBtn;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_movie_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Slide(Gravity.BOTTOM));
            getWindow().setAllowEnterTransitionOverlap(true);
        }

        id = getIntent().getStringExtra(getResources().getString(R.string.extraID));
        String title =  getIntent().getStringExtra(getResources().getString(R.string.extraTitle));
        String posterPath = getIntent().getStringExtra(getResources().getString(R.string.extraPath));

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
        mFab = findViewById(R.id.fav_fab);
        backBtn = findViewById(R.id.back_arrow);
        appBarLayout = findViewById(R.id.app_bar);

        bundle = new Bundle();
        bundle.putString(getResources().getString(R.string.extraID),id);

        database = FavRoomDatabase.getDatabase(this.getApplicationContext());
        viewModel = ViewModelProviders.of(this).get(FavViewModel.class);

        roomMovieId = database.favouriteDao().loadMovieById(id);

        roomMovieId.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if (s == null) {
                    mFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else if (s.equals(id)) {

                    mFab.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final SearchResult favourite = new SearchResult(id,posterPath,title);
                FavouriteState(favourite);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(MovieDetailsActivity.this);
                finish();
            }
        });
    }


    private void FavouriteState(SearchResult favourite) {
        String value = roomMovieId.getValue();
        if(value==null)
        {
            mFab.setImageResource(R.drawable.ic_favorite_black_24dp);
            viewModel.insert(favourite);

        }
        else if (value.equals(id))
        {
            mFab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            viewModel.delete(favourite);
        }
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
                Picasso.with(MovieDetailsActivity.this).load(getResources().getString(R.string.images_url) + coverPathtwo).into(mCoverPhoto);
            }
            else {
                if (coverPath != null)
                Picasso.with(MovieDetailsActivity.this).load(getResources().getString(R.string.images_url) + coverPath).into(mCoverPhoto);
            }


            String posterPathtwo = model.getPosterPath();
            if (posterPathtwo != null) {
                Picasso.with(MovieDetailsActivity.this).load(getResources().getString(R.string.images_url) + posterPathtwo).into(mMainPhoto);
            }
            else{
                if (mainPath != null)
                Picasso.with(MovieDetailsActivity.this).load(getResources().getString(R.string.images_url) + mainPath).into(mMainPhoto);
            }

            String movieTitle = model.getTitle();
            String runTime = String.valueOf(model.getRuntime());
            String year = model.getReleaseDate();
            String rate = String.valueOf(model.getVoteAverage());
            overView = model.getOverview();

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


            viewPagerAdapter = new MovieViewPagerAdapter(getSupportFragmentManager());

            bundle.putString(getResources().getString(R.string.overview_detail),overView);
            MovieInfoFrag movieInfoFrag = new MovieInfoFrag();
            movieInfoFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieInfoFrag,getResources().getString(R.string.Info_frag));

            MovieImagesVidsFrag imagesVidsFrag = new MovieImagesVidsFrag();
            imagesVidsFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(imagesVidsFrag,getResources().getString(R.string.Images_Videos_frag));


            MovieReviewsFrag movieReviewsFrag = new MovieReviewsFrag();
            movieReviewsFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieReviewsFrag,getResources().getString(R.string.Reviews_frag));


            MovieSimilarFrag movieSimilarFrag = new MovieSimilarFrag();
            movieSimilarFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieSimilarFrag,getResources().getString(R.string.Similar_Movies_frag));


            MovieRecomFrag movieRecomFrag = new MovieRecomFrag();
            movieRecomFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieRecomFrag,getResources().getString(R.string.Recommendations_frag));

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onFail(String error) {
        Log.e("MovieDetailsActivity",error);
    }
}
