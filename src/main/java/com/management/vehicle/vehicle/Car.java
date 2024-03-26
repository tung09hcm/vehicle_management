package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;

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

    public Car(String DriverID,int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license, String customername, String customerphoneNumber, String customerid, String customeraddress, String rentalDate, String returnDate) {
        super(DriverID,distanceCover, type, length, wide, high, plateNumber, weight, status, license);
        Customername = customername;
        CustomerphoneNumber = customerphoneNumber;
        Customerid = customerid;
        Customeraddress = customeraddress;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

}
