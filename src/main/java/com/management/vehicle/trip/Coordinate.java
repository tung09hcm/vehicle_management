package com.management.vehicle.trip;

public class Coordinate {
    private double lng;
    private double lat;
    public Coordinate() {
        lng = 0;
        lat = 0;
    }
    public Coordinate(double x, double y) {
        this.lng = x;
        this.lat = y;
    }

    public double getLng() {
        return lng;
    }
    public double getLat() {
        return lat;
    }
    public void setLng(double x) {
        this.lng = x;
    }
    public void setLat(double y) {
        this.lat = y;
    }
}
