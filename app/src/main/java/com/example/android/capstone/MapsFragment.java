package com.example.android.capstone;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Arrays;

//found help from these tutorials playlist: https://www.youtube.com/playlist?list=PLgCYzUzKIBE-vInwQhGSdnbyJ62nixHCt
//found help with nearby places from google developers documentation
//https://developers.google.com/places/web-service/search#PlaceSearchRequests
public class MapsFragment extends Fragment  implements OnMapReadyCallback, onResponce {
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
    private FirebaseAnalytics mFirebaseAnalytics;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mGps = view.findViewById(R.id.ic_gps);
        mLocalMovies = view.findViewById(R.id.ic_local_movies);
        mMorePlaces = view.findViewById(R.id.btn_more_cinemas);
        volleyUtils = new VolleyUtils();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());


        if (isServicesOk()) {
            getLocationPermission();
        }
        return view;
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        mLocalMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNearByCinemas();
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getContext().getApplicationContext(), getResources().getString(R.string.GOOGLE_PLAY_API_KEY));
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                    if (place.getLatLng() != null && place.getName() != null)
                    moveCamera(place.getLatLng(),DEFAULT_ZOOM,place.getName());
                    else {
                        Log.e("PLACES_TAG","An error occurred in places");
                    }
                }

                @Override
                public void onError(Status status) {
                    Log.i("TAG", "An error occurred: " + status);
                    Toast.makeText(getContext(),"An error occurred, please try again.",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getNearByCinemas() {
        if (!mClearAll) {
            if (currentLatLng != null) {
                UrlString = getResources().getString(R.string.NearBySearch_first) +
                        currentLatLng.latitude + "," + currentLatLng.longitude +
                        getResources().getString(R.string.NearBySearch_second)+ getResources().getString(R.string.GOOGLE_PLAY_API_KEY);

                volleyUtils.volleyNearByResults(UrlString, getContext(), this);
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

        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation() {
        mLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    moveCamera(currentLatLng, DEFAULT_ZOOM, "You're Here!");
                                } else {
                                    Toast.makeText(getActivity(), "Unable to locate current location," +
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
                            Toast.makeText(getActivity(), "Please grant the requested permissions so the app can work correctly", Toast.LENGTH_LONG).show();
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
        //  Toast.makeText(MapsActivity.this, "Map is ready to use", Toast.LENGTH_LONG).show();
        mMap = googleMap;
        if (mLocationPermissionGranted)
            getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    public boolean isServicesOk(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if (available == ConnectionResult.SUCCESS){
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(getContext(),"Please make sure you have the latest google play services installed on your device",Toast.LENGTH_LONG).show();
        }
        return false;
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
        Log.e("MapsActivity",error);
    }

    private void FindNextResults(String nextPageResult) {
        UrlString = getResources().getString(R.string.NextPageMapsUrl_first) +
                getResources().getString(R.string.GOOGLE_PLAY_API_KEY) + "&pagetoken=" +nextPageResult;
        volleyUtils.volleyNearByResults(UrlString,getContext(),this);
    }


}
