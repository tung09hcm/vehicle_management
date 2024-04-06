package com.management.vehicle.request;

import com.management.vehicle.trip.Coordinate;

import java.util.List;

public class RouteMatrix {
    private double distance;
    private int duration;
    private List<Coordinate> coordinates;

    public RouteMatrix() {
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
