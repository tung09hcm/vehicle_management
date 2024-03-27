package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;

public class Container extends Vehicle{
    private String goodsType;
    private double goodsWeight;

    public String getGoodsType() {
        return goodsType;
    }

    public double getKhoiLuongHangHoa() {
        return goodsWeight;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Container(String DriverID, int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license, String goodsType, double goodsWeight ){
        super(DriverID,distanceCover, type, length, wide, high, plateNumber, weight, status, license);
        goodsType = goodsType;
        goodsWeight = goodsWeight;
    }

    public Container()
    {
        super();
        this.setLicense(LicenseLevel.FC);
        goodsType = "";
        goodsWeight = 0;
    }
}

