package com.example.android.capstone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.capstone.moviemodel.SearchResult;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onMainResponce;

import java.util.ArrayList;

public class MainActivity extends CustomAppCompat {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        start();

    }
    private void start() {
        if (checkInternetConnection()) {
            if (sharedPreferences.getString(getResources().getString(R.string.state),getResources().getString(R.string.movies_key)).equals(getResources().getString(R.string.movies_key))) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MoviesFragment())
                        .commitAllowingStateLoss();
                editor.putString(getResources().getString(R.string.state),getResources().getString(R.string.movies_key));
                editor.apply();
            }
            else if (sharedPreferences.getString((getResources().getString(R.string.state)),getResources().getString(R.string.movies_key)).equals(getResources().getString(R.string.map))){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MapsFragment())
                            .commit();
                    editor.putString(getResources().getString(R.string.state),getResources().getString(R.string.map));
                    editor.apply();
                }
            else if (sharedPreferences.getString((getResources().getString(R.string.state)),getResources().getString(R.string.movies_key)).equals(getResources().getString(R.string.fav_type)))  {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FavFragment())
                        .commit();
                editor.putString((getResources().getString(R.string.state)),getResources().getString(R.string.fav_type));
                editor.apply();
            }

            }
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.action_movies:
                            //  Toast.makeText(MainActivity.this, "main", Toast.LENGTH_LONG).show();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new MoviesFragment())
                                    .commit();
                            editor.putString((getResources().getString(R.string.state)),getResources().getString(R.string.movies_key));
                            editor.apply();

                            break;

                        case R.id.action_fav:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new FavFragment())
                                    .commit();
                            editor.putString((getResources().getString(R.string.state)),getResources().getString(R.string.fav_type));
                            editor.apply();
                            break;

                        case R.id.action_locate:
                            //  Toast.makeText(MainActivity.this,"locate",Toast.LENGTH_LONG).show();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new MapsFragment())
                                    .commit();
                            editor.putString((getResources().getString(R.string.state)),getResources().getString(R.string.map));
                            editor.apply();
                            break;
                    }
                    return true;
                }
            });



        }

    private boolean checkInternetConnection () {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
//                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
            return true;
        } else {
            // Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.dialog_message))
                    .setTitle(getResources().getString(R.string.dialog_title));
            builder.setCancelable(false);
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    start();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            //https://stackoverflow.com/questions/4095758/change-button-color-in-alertdialog/22069936
            Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

            if (b != null) {
                b.setTextColor(getResources().getColor(R.color.blue));
            }
        }
        return false;
    }


}


