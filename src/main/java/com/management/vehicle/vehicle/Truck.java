package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;

import java.util.List;


public class Truck extends Vehicle{
    private String goodsType;
    private double goodsWeight;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Truck(List<Trip> history,String driverID, int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license, String goodsType, double goodsWeight) {
        super(history,driverID, distanceCover, type, length, wide, high, plateNumber, weight, status, license);
        this.goodsType = goodsType;
        this.goodsWeight = goodsWeight;
    }

    public Truck()
    {
        super();
        this.setLicense(LicenseLevel.F);
        this.goodsType = "";
        this.goodsWeight = 0;
    }

}

