package com.theroboticsforum.sihapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
Activity that allows user to plan his trip from his/her current
 location to pickup points using Shortest path algorithm
 */

public class PlanTripActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);
    }
}
