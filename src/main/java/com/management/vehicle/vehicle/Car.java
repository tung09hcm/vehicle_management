package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;

import java.util.List;

public class Car extends Vehicle {
    private String Customername;
    private String CustomerphoneNumber;
    private String Customerid;
    private String Customeraddress;
    private String rentalDate;
    private String returnDate;


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

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Car()
    {
        super();
        this.setLicense(LicenseLevel.B1);
        Customername = "";
        CustomerphoneNumber = "";
        Customerid = "";
        Customeraddress = "";
        this.rentalDate = "";
        this.returnDate = "";
        setMaintenanceCycleInDays(120);
        setMaintenanceCycleInKilometers(5000);
        setLimitKilometers(6000);
        setFuel_per_kilometer(0.05);
    }

}
