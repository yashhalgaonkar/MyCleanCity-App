package com.theroboticsforum.sihapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    //vars
    private Context mCtx;
    private ArrayList<LocationHelper> mPointsList;

    public CustomAdapter(Context mCtx, ArrayList<LocationHelper> mPointsList) {
        this.mCtx = mCtx;
        this.mPointsList = mPointsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_points , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*final Points mPoint = mPointsList.get(position);
        holder.mainAdd.setText(mPoint.getMainAdd());
        holder.subAdd.setText(mPoint.getSubAdd());
        Random rnd = new Random();
        holder.rating.setText(Double.toString(rnd.nextInt(10)));


        holder.viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "Maps", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mCtx , MapActivity.class);
                mCtx.startActivity(i);
            }
        });
        holder.viewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "Photos", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mCtx , ImageActivity.class);
                mCtx.startActivity(i);
            }
        });

         */
        final LocationHelper helper = mPointsList.get(position);
        holder.latitude.setText("Latitude: " + helper.getLatitude().toString());
        holder.longitude.setText("Longitude: " + helper.getLongitude().toString());

        holder.viewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx , ImageActivity.class);
                mCtx.startActivity(i);
            }
        });

        holder.viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx , MapActivity.class);
                i.putExtra("lat" , helper.getLatitude());
                i.putExtra("lng" , helper.getLongitude());
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPointsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //widgets
        /*
        private RelativeLayout viewPhotos, viewMap;
        private TextView date, time;
        private TextView mainAdd, subAdd;
        private TextView rating;

         */

        private TextView latitude, longitude;
        private RelativeLayout viewPhotos, viewMap;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //find the widgets
            /*
            viewPhotos = itemView.findViewById(R.id.view_photos);
            viewMap = itemView.findViewById(R.id.view_map);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            mainAdd = itemView.findViewById(R.id.main_add);
            subAdd = itemView.findViewById(R.id.sub_add);
            rating = itemView.findViewById(R.id.rating);

             */
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
            viewPhotos = itemView.findViewById(R.id.view_photos);
            viewMap = itemView.findViewById(R.id.view_map);

        }
    }
}
