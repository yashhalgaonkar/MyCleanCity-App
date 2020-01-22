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

public class MainActivity extends AppCompatActivity {


    //widgets
    private GridLayout mainGrid;



    //vars
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

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
                                    finish();
                                    break;
                                case 1:
                                    //add pick up
                                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    //plan a trip
                                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    //signout
                                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
            );
        }
    }

}
