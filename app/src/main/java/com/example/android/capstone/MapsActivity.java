package com.example.android.capstone;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.capstone.MapModel.Geometry;
import com.example.android.capstone.MapModel.NearByFullModel;
import com.example.android.capstone.MapModel.Result;
import com.example.android.capstone.volleyUtils.VolleyUtils;
import com.example.android.capstone.volleyUtils.onResponce;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//found help from these tutorials playlist: https://www.youtube.com/playlist?list=PLgCYzUzKIBE-vInwQhGSdnbyJ62nixHCt
//found help with nearby places from google developers documentation
//https://developers.google.com/places/web-service/search#PlaceSearchRequests
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, onResponce {

    private GoogleMap mMap;
    int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_REQUEST_CODE = 1111;
    private FusedLocationProviderClient mLocationClient;
    private static final float DEFAULT_ZOOM = 15f;
    private static final float NEAR_BY_ZOOM = 10f;
    private ImageView mGps;
    private ImageView mLocalMovies;
    private LatLng currentLatLng;
    private String UrlString;
    private VolleyUtils volleyUtils;
    private ImageView mMorePlaces;
    private Boolean mClearAll = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mGps = findViewById(R.id.ic_gps);
        mLocalMovies = findViewById(R.id.ic_local_movies);
        mMorePlaces = findViewById(R.id.btn_more_cinemas);
        volleyUtils = new VolleyUtils();


        if (isServicesOk()) {
            getLocationPermission();
        }
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();

        mLocalMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNearByCinemas();
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyC97nMpETrLaMBqQKyXlYSvW-cECS5BBQA");
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                    moveCamera(place.getLatLng(),DEFAULT_ZOOM,place.getName());
                }

                @Override
                public void onError(Status status) {
                    Log.i("TAG", "An error occurred: " + status);
                    Toast.makeText(MapsActivity.this,"An error occurred, please try again.",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getNearByCinemas() {
        if (!mClearAll) {
            if (currentLatLng != null) {
                UrlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                        currentLatLng.latitude + "," + currentLatLng.longitude +
                        "&radius=20000&type=movie_theater&keyword=cinema&key=AIzaSyC97nMpETrLaMBqQKyXlYSvW-cECS5BBQA";

                volleyUtils.volleyNearByResults(UrlString, MapsActivity.this, MapsActivity.this);
            }
        }
        else
        {
            mMap.clear();
            mClearAll = false;
        }

    }


    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation() {
        mLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    moveCamera(currentLatLng, DEFAULT_ZOOM, "You're Here!");
                                } else {
                                    Toast.makeText(MapsActivity.this, "Unable to locate current location," +
                                            " Please make sure that the location is activated in your settings.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        } catch (SecurityException e) {
            Log.e("MapsException", e.toString());
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String line) {
        if (!line.equals("You're Here!"))
        mMap.addMarker(new MarkerOptions().position(latLng).title(line));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Toast.makeText(MapsActivity.this, "Please grant the requested permissions so the app can work correctly", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }


        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(MapsActivity.this, "Map is ready to use", Toast.LENGTH_LONG).show();
        mMap = googleMap;
        if (mLocationPermissionGranted)
            getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    public boolean isServicesOk(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (available == ConnectionResult.SUCCESS){
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(MapsActivity.this,"Please make sure you have the latest google play services installed on your device",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onSuccess(Object responce, Object mainResponce) {
        ArrayList<Result> results = (ArrayList<Result>) responce;
        NearByFullModel nearByFullModel = (NearByFullModel) mainResponce;
        for (int i = 0 ; i < results.size() ; i++){
            Result result = results.get(i);
            Geometry geometry = result.getGeometry();
            com.example.android.capstone.MapModel.Location location = geometry.getLocation();
            LatLng latLng = new LatLng(location.getLat(),location.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title(result.getName()));
            }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, NEAR_BY_ZOOM));
        String nextPageResult = nearByFullModel.getNextPageToken();
        if (nextPageResult != null) {
            mMorePlaces.setVisibility(View.VISIBLE);
            mMorePlaces.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FindNextResults(nextPageResult);
                }
            });
            mLocalMovies.setVisibility(View.INVISIBLE);

        }
        else {
            mMorePlaces.setVisibility(View.GONE);
            mLocalMovies.setVisibility(View.VISIBLE);
            mClearAll = true;

        }
    }

    @Override
    public void onFail(String error) {

    }

    private void FindNextResults(String nextPageResult) {
        UrlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "&key=AIzaSyC97nMpETrLaMBqQKyXlYSvW-cECS5BBQA&pagetoken="+nextPageResult;
        volleyUtils.volleyNearByResults(UrlString,MapsActivity.this,MapsActivity.this);
    }
}
