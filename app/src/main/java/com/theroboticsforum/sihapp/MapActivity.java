package com.theroboticsforum.sihapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //vars
    private static final String TAG = "MapActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;

    //google
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location currentLocation;


    //widgets
    private TextView address;

    //vars
    private  Double lat,lng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG, "onCreate: Started");

        address = findViewById(R.id.address);
        lat = getIntent().getDoubleExtra("lat" , 18.4641);
        lng = getIntent().getDoubleExtra("lng" ,73.8676 );





        //to initialize the map if permissions are granted
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: called");

        /*
        LatLng vit = new LatLng(18.4641, 73.8676);
        LatLng coep = new LatLng(18.5293  , 73.8565);
        LatLng vjti = new LatLng(19.0222 , 72.8561);
        LatLng iitb = new LatLng(19.1334, 72.9193);
        LatLng pict = new LatLng(18.4575 , 73.8508);
        LatLng gov_latur  = new LatLng(18.3943 , 76.5629);
        LatLng gov_poly = new LatLng(18.5388 , 73.8320);

         */

        LatLng location = new LatLng(lat , lng);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Marker in VIT"));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(vit));
        //moveCamera(vit,DEFAULT_ZOOM);
    }

    private void moveCamera(LatLng latLng,float zoom){
        Log.d(TAG, "moveCamera: moving the camera to lat: "+latLng.latitude+
                " long: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
}
