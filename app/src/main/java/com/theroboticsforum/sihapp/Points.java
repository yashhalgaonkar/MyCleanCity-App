package com.theroboticsforum.sihapp;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.sql.Timestamp;
import java.util.Map;

public class Points {
    private String mainAdd;
    private String subAdd;
    private Double rating;


    public Points()
    {
        //empty constructor
    }

    public Points(String mainAdd, String subAdd, Double rating) {
        this.mainAdd = mainAdd;
        this.subAdd = subAdd;
        this.rating = rating;
    }

    public String getMainAdd() {
        return mainAdd;
    }

    public void setMainAdd(String mainAdd) {
        this.mainAdd = mainAdd;
    }

    public String getSubAdd() {
        return subAdd;
    }

    public void setSubAdd(String subAdd) {
        this.subAdd = subAdd;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
