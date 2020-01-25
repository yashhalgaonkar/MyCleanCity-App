package com.theroboticsforum.sihapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

/*
Activity to show all the pickups , their latitudes and longitudes , photos adnd ploting them on maps
 */

public class PickUpActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    //widgets
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;

    //vars
    private CustomAdapter customAdapter;
    private ArrayList<LocationHelper> mPointList = new ArrayList<>();

    //firebas realtie
    //private FirebaseFirestore mLocationData = FirebaseFirestore.getInstance();

    //firebase realatime
    private DatabaseReference mLocationData = FirebaseDatabase.getInstance().getReference("locations");

    //vars
    private static final String TAG = "PickUpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        customAdapter = new CustomAdapter(this , mPointList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(customAdapter);

        getPoints();

        /* Dummy Data
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

         */



    }

    private void getPoints()
    {
        //method to get points from firebase and store them in ArrayList
        /*mLocationData.collection("locations").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                mPointList.clear();
                for(DocumentSnapshot point : queryDocumentSnapshots.getDocuments())
                {
                    Points mpoint = point.toObject(Points.class);
                    mPointList.add(mpoint);
                }
                customAdapter.notifyDataSetChanged();
            }
        });

         */

        mLocationData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPointList.clear();
                for(DataSnapshot location : dataSnapshot.getChildren())
                {
                    LocationHelper helper = location.getValue(LocationHelper.class);
                    mPointList.add(helper);
                }
                Log.d(TAG, "onDataChange: list "+mPointList.toString());
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ",databaseError.toException() );
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        getPoints();
    }

    @Override
    public void onRefresh() {
        getPoints();
        refreshLayout.setRefreshing(false);
    }
}
