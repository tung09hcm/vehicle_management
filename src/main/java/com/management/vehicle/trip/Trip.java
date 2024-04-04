package com.management.vehicle.trip;

public class Trip {
    public void Print()
    {
        System.out.println("--------------------------");
        System.out.println("begin: " + begin);
        System.out.println("tripID: " + tripID);
        System.out.println("end: " + end);
        System.out.println("begin_date: " + begin_date);
        System.out.println("end_date: " + end_date);
        System.out.println("plateNumber: " + plateNumber);
        System.out.println("driverID: " + driverID);
        System.out.println("Revenue: " + Revenue);
        System.out.println("--------------------------");
    }
    private Coordinate begin;
    private String tripID;
    private Coordinate end;
    private String begin_date;
    private String end_date;
    private String plateNumber;
    private String driverID;

    private double Revenue;

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
        this.begin = new Coordinate();
        this.end = new Coordinate();
        this.begin_date = "";
        this.end_date = "";
        this.driverID = "";
        this.plateNumber = "";
        this.tripID = "";
        this.Revenue = 0;
    }


}
