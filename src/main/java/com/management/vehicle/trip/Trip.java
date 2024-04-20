package com.management.vehicle.trip;
import com.management.vehicle.request.RouteMatrix;
import com.management.vehicle.request.struct.*;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.vehicle.fuel;

import java.util.List;

public class Trip {
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("--------------------------\n");
        buffer.append("begin: ").append(begin).append("\n");
        buffer.append("tripID: ").append(tripID).append("\n");
        buffer.append("end: ").append(end).append("\n");
        buffer.append("begin_date: ").append(begin_date).append("\n");
        buffer.append("end_date: ").append(end_date).append("\n");
        buffer.append("plateNumber: ").append(plateNumber).append("\n");
        buffer.append("driverID: ").append(driverID).append("\n");
        buffer.append("Revenue: ").append(Revenue).append("\n");
        buffer.append("Distance: ").append(distanceCover).append("\n");
        buffer.append("--------------------------\n");

        return buffer.toString();
    }
    private TripStatus status;

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }
    private fuel fuel_trip = fuel.NONE;
    private String beginLocation;
    private String endLocation;
    private double distanceCover;
    private Coordinate begin;
    private String tripID;
    private Coordinate end;
    private String begin_date;
    private String end_date;
    private String plateNumber;
    private String driverID;
    private double cost;
    private double Revenue;

    public fuel getFuel_trip() {
        return fuel_trip;
    }

    public void setFuel_trip(fuel fuel_trip) {
        this.fuel_trip = fuel_trip;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getBeginLocation() {
        return beginLocation;
    }

    public void setBeginLocation(String beginLocation) {
        this.beginLocation = beginLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getDistanceCover() {
        return distanceCover;
    }

    public void setDistanceCover(double distanceCover) {
        this.distanceCover = distanceCover;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public Coordinate getBegin() {
        return begin;
    }
    public Coordinate getEnd() {
        return end;
    }
    public String getBegin_date() {
        return begin_date;
    }
    public String getEnd_date() {
        return end_date;
    }
    public void setBegin(Coordinate begin) {
        this.begin = begin;
    }
    public void setEnd(Coordinate end) {
        this.end = end;
    }
    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    public double getRevenue() {
        return Revenue;
    }
    public void setRevenue(double revenue) {
        Revenue = revenue;
    }



    public Trip() {
        this.distanceCover = 0;
        this.begin = new Coordinate();
        this.end = new Coordinate();
        this.begin_date = "";
        this.end_date = "";
        this.driverID = "";
        this.plateNumber = "";
        this.tripID = "";
        this.Revenue = 0;
        this.status = TripStatus.NONE;
    }


}