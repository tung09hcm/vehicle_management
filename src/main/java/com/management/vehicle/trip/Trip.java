package com.management.vehicle.trip;
import com.management.vehicle.request.RouteMatrix;
import com.management.vehicle.request.struct.*;
import com.management.vehicle.request.MapRequest;

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

    private double distanceCover;
    private Coordinate begin;
    private String tripID;
    private Coordinate end;
    private String begin_date;
    private String end_date;
    private String plateNumber;
    private String driverID;

    private double Revenue;

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

    public void makeTrip(String from , String to) throws Exception {
        MapRequest mapRequest = new MapRequest();
        List coordinate1 = mapRequest.getCoordinateList(from);
        List coordinate2 = mapRequest.getCoordinateList(to);

        RouteMatrix result = mapRequest.getDistanceMatrix(coordinate1,coordinate2);


        System.out.println(result.getDistance());
        System.out.println(result.getDuration());
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
