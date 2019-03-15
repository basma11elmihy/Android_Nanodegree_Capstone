package com.example.android.capstone;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        id = getIntent().getStringExtra("extraID");
        String title =  getIntent().getStringExtra("extraTitle");
        String posterPath = getIntent().getStringExtra("extraPath");

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

        bundle = new Bundle();
        bundle.putString("extraID",id);

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

            bundle.putString("overview",overView);
            MovieInfoFrag movieInfoFrag = new MovieInfoFrag();
            movieInfoFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieInfoFrag,"Info");

            MovieImagesVidsFrag imagesVidsFrag = new MovieImagesVidsFrag();
            imagesVidsFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(imagesVidsFrag,"Images & Videos");


            MovieReviewsFrag movieReviewsFrag = new MovieReviewsFrag();
            movieReviewsFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieReviewsFrag,"Reviews");


            MovieSimilarFrag movieSimilarFrag = new MovieSimilarFrag();
            movieSimilarFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieSimilarFrag,"Similar Movies");


            MovieRecomFrag movieRecomFrag = new MovieRecomFrag();
            movieRecomFrag.setArguments(bundle);
            viewPagerAdapter.addFragment(movieRecomFrag,"Recommendations");

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onFail(String error) {

    }
}
