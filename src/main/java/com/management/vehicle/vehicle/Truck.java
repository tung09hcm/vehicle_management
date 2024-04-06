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

    public Truck()
    {
        super();
        this.setLicense(LicenseLevel.F);
        this.goodsType = "";
        this.goodsWeight = 0;
        setMaintenanceCycleInMonths(180);
        setMaintenanceCycleInKilometers(8000);
    }


}

