package com.theroboticsforum.sihapp;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
/*
Activity to allow the user to add pickup manually by adding photo..
*/

public class AddPickUpActivity extends AppCompatActivity implements View.OnClickListener {

    //vars
    private static final String TAG = "AddPickUpActivity";
    private final int PICK_IMAGE_REQUEST = 22;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;

    //widgets
    private RelativeLayout mUploadButton, mChooseBtn;
    private ProgressBar mProgress;

    //firebase database
    private DatabaseReference mLocationData = FirebaseDatabase.getInstance().getReference("locations");
    private Uri localFilePath;
    private String serverFilePath;

    //firebas estorage
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //maps
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pick_up);


        //find the widgtes
        mProgress = findViewById(R.id.loading);
        mUploadButton = findViewById(R.id.upload_btn);
        mChooseBtn = findViewById(R.id.choose_btn);

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        mUploadButton.setOnClickListener(this);
        mChooseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mProgress.setVisibility(View.VISIBLE);


        if (view.getId() == R.id.upload_btn) {
            uploadImage();
            getCurrentLocation();
            pushToFirestore();
            mProgress.setVisibility(View.INVISIBLE);
        }

        if (view.getId() == R.id.choose_btn) {
            SelectImage();
            mProgress.setVisibility(View.INVISIBLE);
        }

    }

    // Select Image method
    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            localFilePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                localFilePath);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (localFilePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            serverFilePath = "images/" + UUID.randomUUID().toString();
            StorageReference ref = storageReference.child(serverFilePath);

            // adding listeners on upload
            // or failure of image
            ref.putFile(localFilePath).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(
                                UploadTask.TaskSnapshot taskSnapshot) {

                            // Image uploaded successfully
                            // Dismiss dialog
                            progressDialog.dismiss();
                            Toast.makeText(AddPickUpActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(AddPickUpActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot) {
                            double progress
                                    = (100.0
                                    * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(
                                    "Uploaded "
                                            + (int) progress + "%");
                        }
                    });
        }
    }

    //method to get current user location
    private void getCurrentLocation()
    {
        if(isServicesOk()){
            //isServiceOk checks if google play services are OK
            getLocationPermission();
        }

        //getAddress();


    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: Getting location permissions.");
        //get permissions from the user implicitly
        String[] permissons = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                //permissions have been granted already and we are good to go
                mLocationGranted = true;
                Log.d(TAG, "getLocationPermission: Permissions have been granted.");
                //all permissions done...start the map
                //initMap();
                getDeviceLocation();

            }else{
                //we need to take the permissions and onRequestPermissionsResult will be called
                ActivityCompat.requestPermissions(this,
                        permissons,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            //we need to take the permissions and onRequestPermissionsResult will be called
            ActivityCompat.requestPermissions(this,
                    permissons,
                    LOCATION_PERMISSION_REQUEST_CODE);}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Taking permissions implicitly.");
        mLocationGranted = false;
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length >0){
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permissions were not granted.");
                            return;
                        }
                    }
                    mLocationGranted = true;
                    Log.d(TAG, "onRequestPermissionsResult: permissions have been granted.");
                    //init our map
                    //all permissions granted.. start the map...
                    //initMap();
                    getDeviceLocation();

                }
            }
        }
    }

    public boolean isServicesOk(){
        Log.d(TAG, "isServicesOk: Cheacking Google Services version.");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AddPickUpActivity.this);

        if(available== ConnectionResult.SUCCESS){
            //everything is fine and user can amke map request
            Log.d(TAG, "isServicesOk: Google Play Services is wroking");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOk: an error occured but we can fix it.");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AddPickUpActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You cant make map request.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: Getting device current location.");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if(mLocationGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(
                        new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "onComplete: Found Location.");
                                    currentLocation = (Location)task.getResult();
                                }
                                else{
                                    Log.d(TAG, "onComplete: Current Location is null.");
                                    Toast.makeText(AddPickUpActivity.this,
                                            "Cannot get current Location.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }

        }catch(SecurityException e){
            Log.e(TAG, "getDeviceLocation: Security Exception.",e );
        }

    }

    private String getAddress() {
        Log.d(TAG, "getAddress: Called.");
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(),
                    currentLocation.getLongitude(), 1);
            if (addresses != null) {

                strAdd = addresses.get(0).getLocality();//gives the city

                //Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;

    }

    //to store latitute and longitiude to firebase
    private void pushToFirestore()
    {
        LocationHelper helper = new LocationHelper(currentLocation.getLatitude() ,
                currentLocation.getLongitude() , serverFilePath);
        mLocationData.push().setValue(helper).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ",e );
                    }
                }
        );
    }




}
