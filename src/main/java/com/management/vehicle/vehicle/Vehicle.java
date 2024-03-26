package com.management.vehicle.vehicle;
import com.management.vehicle.LicenseLevel;

public class Vehicle {
    private int distanceCover;
    private TypeVehicle type;
    private VehicleStatus status;
    private String DriverID = "null";
    private double length;
    private double wide;
    private double high;
    private String plateNumber;
    private double weight;
    private LicenseLevel license;

    public int getDistanceCover() {
        return distanceCover;
    }

    public void setDistanceCover(int distanceCover) {
        this.distanceCover = distanceCover;
    }

    public TypeVehicle getType() {
        return type;
    }

    public void setType(TypeVehicle type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWide() {
        return wide;
    }

    public void setWide(double wide) {
        this.wide = wide;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public LicenseLevel getLicense() {
        return license;
    }

    public void setLicense(LicenseLevel license) {
        this.license = license;
    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }

    public void getHistory() {

    }
    public void setMaintenance(){}

    public Vehicle(String DriverID,int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license) {
        this.distanceCover = distanceCover;
        this.type = type;
        this.length = length;
        this.wide = wide;
        this.high = high;
        this.plateNumber = plateNumber;
        this.weight = weight;
        this.status = status;
        this.license = license;
        DriverID = DriverID;
    }
}

