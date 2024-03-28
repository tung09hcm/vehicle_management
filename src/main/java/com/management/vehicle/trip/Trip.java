package com.management.vehicle.trip;

public class Trip {
    private Coordinate begin;
    private Coordinate end;
    private String begin_date;
    private String end_date;
    private String plateNumber;
    private String driverID;

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

    public Trip() {
        this.begin = new Coordinate();
        this.end = new Coordinate();
        this.begin_date = "";
        this.end_date = "";
        this.driverID = "";
        this.plateNumber = "";
    }

    public Trip(String driverID, String plateNumber,Coordinate begin, Coordinate end, String begin_date, String end_date) {
        this.begin = begin;
        this.end = end;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.plateNumber = plateNumber;
        this.driverID = driverID;
    }
}
