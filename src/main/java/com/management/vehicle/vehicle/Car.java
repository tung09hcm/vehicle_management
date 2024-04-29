package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;

import java.util.List;

public class Car extends Vehicle {
    private String Customername;
    private String CustomerphoneNumber;
    private String Customerid;
    private String Customeraddress;


    public String getCustomername() {
        return Customername;
    }

    public void setCustomername(String customername) {
        Customername = customername;
    }

    public String getCustomerphoneNumber() {
        return CustomerphoneNumber;
    }

    public void setCustomerphoneNumber(String customerphoneNumber) {
        CustomerphoneNumber = customerphoneNumber;
    }

    public String getCustomerid() {
        return Customerid;
    }

    public void setCustomerid(String customerid) {
        Customerid = customerid;
    }

    public String getCustomeraddress() {
        return Customeraddress;
    }

    public void setCustomeraddress(String customeraddress) {
        Customeraddress = customeraddress;
    }

    public Car()
    {
        super();
        this.setLicense(LicenseLevel.B1);
        Customername = "";
        CustomerphoneNumber = "";
        Customerid = "";
        Customeraddress = "";
        setMaintenanceCycleInDays(120);
        setMaintenanceCycleInKilometers(5000);
        setLimitKilometers(6000);
        setFuel_per_kilometer(0.05);
        setFuel_v(fuel.RON95);
    }

}
