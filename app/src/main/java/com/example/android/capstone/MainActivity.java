package com.example.android.capstone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        start();

    }
    private void start() {
        if (checkInternetConnection()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,new MoviesFragment())
                    .commit();
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.action_movies:
                            //  Toast.makeText(MainActivity.this, "main", Toast.LENGTH_LONG).show();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new MoviesFragment())
                                    .commit();

                            break;

                        case R.id.action_fav:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new FavFragment())
                                    .commit();
                            break;

                        case R.id.action_locate:
                            //  Toast.makeText(MainActivity.this,"locate",Toast.LENGTH_LONG).show();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new MapsFragment())
                                    .commit();
                            break;
                    }
                    return true;
                }
            });



        }
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


