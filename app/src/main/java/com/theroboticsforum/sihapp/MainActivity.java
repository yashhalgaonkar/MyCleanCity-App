package com.theroboticsforum.sihapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //widgets
    private GridLayout mainGrid;

    //firebase authetication
    private FirebaseAuth.AuthStateListener mAuthListener;



    //vars
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().hide();

        setupFirebaseAuth();

        mainGrid = findViewById(R.id.mainGrid);
        setClickEvent(mainGrid);


    }

    private void setClickEvent(GridLayout mainGrid)
    {
        Log.d(TAG, "setClickEvent: called");
        for(int i=0; i<mainGrid.getChildCount(); ++i)
        {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            
                            switch (finalI)
                            {
                                case 0:
                                    //pick up points
                                    startActivity(new Intent(MainActivity.this , PickUpActivity.class));
                                    break;
                                case 1:
                                    //add pick up
                                    startActivity(new Intent(MainActivity.this , AddPickUpActivity.class));
                                    break;
                                case 2:
                                    //plan a trip
                                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    //signout
                                    signOut();
                                    Toast.makeText(MainActivity.this, "Bye Bye", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
            );
        }
    }

    //log out the current user
    private void signOut() {
        Log.d(TAG, "signOut: sigining out.");
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Bye! Bye! ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "signOut: User signed out");
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");
        FirebaseApp.initializeApp(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //currentUserEmail = user.getEmail();

                } else {
                    //user is signed out... revert to login page
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

}
