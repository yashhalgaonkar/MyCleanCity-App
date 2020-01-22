package com.theroboticsforum.sihapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    //vars
    private Context mCtx;
    private ArrayList<Points> mPointsList;

    public CustomAdapter(Context mCtx, ArrayList<Points> mPointsList) {
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
        Points mPoint = mPointsList.get(position);
        holder.mainAdd.setText(mPoint.getMainAdd());
        holder.subAdd.setText(mPoint.getSubAdd());
        holder.rating.setText(String.valueOf(mPoint.getRating()));
        holder.date.setText(mPoint.getDate());
        holder.time.setText(mPoint.getTime());

        holder.viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "Maps", Toast.LENGTH_SHORT).show();
            }
        });
        holder.viewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "Photos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPointsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //widgets
        private RelativeLayout viewPhotos, viewMap;
        private TextView date, time;
        private TextView mainAdd, subAdd;
        private TextView rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //find the widgets
            viewPhotos = itemView.findViewById(R.id.view_photos);
            viewMap = itemView.findViewById(R.id.view_map);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            mainAdd = itemView.findViewById(R.id.main_add);
            subAdd = itemView.findViewById(R.id.sub_add);
            rating = itemView.findViewById(R.id.rating);

        }
    }
}
