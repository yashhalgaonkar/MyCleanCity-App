package com.theroboticsforum.sihapp;

public class LocationHelper {
    private Double latitude;
    private Double longitude;


    public LocationHelper(Double latitude, Double longitude, String path) {
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public LocationHelper()
    {
        //empty constructor
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
