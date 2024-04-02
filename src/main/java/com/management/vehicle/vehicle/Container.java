package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;

import java.util.List;

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


    public Container()
    {
        super();
        this.setLicense(LicenseLevel.FC);
        goodsType = "";
        goodsWeight = 0;
    }
}

