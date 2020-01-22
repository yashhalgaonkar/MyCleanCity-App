package com.theroboticsforum.sihapp;

public class Points {
    private String mainAdd;
    private String subAdd;
    private double rating;
    private String date, time;
    private String latitude;
    private String longitude;

    public Points()
    {
        //empty constructor
    }

    public Points(String mainAdd, String subAdd, double rating, String date, String time) {
        this.mainAdd = mainAdd;
        this.subAdd = subAdd;
        this.rating = rating;
        this.date = date;
        this.time = time;
    }

    public String getMainAdd() {
        return mainAdd;
    }

    public String getSubAdd() {
        return subAdd;
    }

    public double getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
