package com.openweathermap.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon")
    private Double lon;
    @SerializedName("lat")
    private Double lat;

    public Coord() {
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
