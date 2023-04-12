package com.rovaindu.serviesdashboard.model;

import java.io.Serializable;

public class UserAddress implements Serializable {

    private double latitude;
    private double longitude;
    private String placeName;


    public UserAddress(double latitude, double longitude, String placeName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
