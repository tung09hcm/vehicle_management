package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("--------------------------\n");
        buffer.append("distanceCoverFromLastRepair: ").append(distanceCoverFromLastRepair).append("\n");
        buffer.append("last_repair_date: ").append(last_repair_date).append("\n");
        buffer.append("distanceCover: ").append(distanceCover).append("\n");
        buffer.append("type: ").append(type).append("\n");
        buffer.append("status: ").append(status).append("\n");
        buffer.append("driverID: ").append(driverID).append("\n");
        buffer.append("length: ").append(length).append("\n");
        buffer.append("wide: ").append(wide).append("\n");
        buffer.append("high: ").append(high).append("\n");
        buffer.append("plateNumber: ").append(plateNumber).append("\n");
        buffer.append("weight: ").append(weight).append("\n");
        buffer.append("license: ").append(license).append("\n");
        buffer.append("history: ").append(history).append("\n");
        buffer.append("--------------------------\n");
        return buffer.toString();
    }

    private double distanceCoverFromLastRepair;
    private String last_repair_date;
    private double distanceCover;
    private TypeVehicle type;
    private VehicleStatus status;
    private String driverID ;
    private double length;
    private double wide;
    private double high;
    private String plateNumber;
    private double weight;
    private LicenseLevel license;
    private List<String> history;
    public double getDistanceCoverFromLastRepair() {
        return distanceCoverFromLastRepair;
    }

    public void setDistanceCoverFromLastRepair(double distanceCoverFromLastRepair) {
        this.distanceCoverFromLastRepair = distanceCoverFromLastRepair;
    }

    public String getLast_repair_date() {
        return last_repair_date;
    }

    public void setLast_repair_date(String last_repair_date) {
        this.last_repair_date = last_repair_date;
    }


    public double getDistanceCover() {
        return distanceCover;
    }

    public void setDistanceCover(double distanceCover) {
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
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public List<String> getHistory() {
        return history;
    }
    public void setMaintenance(){}

    public Vehicle(String last_repair_date, double distanceCoverFromLastRepair,List<String> history,String driverID,int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license) {
        this.distanceCover = distanceCover;
        this.type = type;
        this.length = length;
        this.wide = wide;
        this.high = high;
        this.plateNumber = plateNumber;
        this.weight = weight;
        this.status = status;
        this.license = license;
        this.driverID = driverID;
        this.history = history;
        this.distanceCoverFromLastRepair = distanceCoverFromLastRepair;
        this.last_repair_date = last_repair_date;
    }
    public Vehicle()
    {
        this.distanceCover = 0;
        this.type = TypeVehicle.NONE;
        this.length = 0;
        this.wide = 0;
        this.high = 0;
        this.plateNumber = "";
        this.weight = 0;
        this.status = VehicleStatus.NONE;
        this.license = LicenseLevel.NONE;
        this.driverID = "";
        this.history = new ArrayList<>();
        this.distanceCoverFromLastRepair = 0;
        this.last_repair_date = "";
    }


}

