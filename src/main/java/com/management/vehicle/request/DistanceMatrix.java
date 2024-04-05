package com.management.vehicle.request;

import com.management.vehicle.trip.Coordinate;

import java.util.List;

public class DistanceMatrix {
    private String destinationAddresses;
    private String originAddresses;
    private double distance;
    private int duration;
    private List<Coordinate> coordinates;
    public DistanceMatrix() {
    }
    public String getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(String destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public String getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(String originAddresses) {
        this.originAddresses = originAddresses;
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
