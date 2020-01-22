package com.theroboticsforum.sihapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class PickUpActivity extends AppCompatActivity {

    //widgets
    private RecyclerView mRecyclerView;

    //vars
    private CustomAdapter customAdapter;
    private ArrayList<Points> mPointList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.recycler_view);

        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));
        mPointList.add(new Points("Vishwakarma Institute Of Technology",
                "666-Upper Indira Nagar, Bibvewadi, Pune, 411048",8.7,"Feb 9, 2020","9:38 am"));


        customAdapter = new CustomAdapter(this , mPointList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(customAdapter);
    }
}
