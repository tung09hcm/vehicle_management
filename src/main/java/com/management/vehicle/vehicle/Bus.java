package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;


public class Bus extends Vehicle {
    private int NumberOfSeat;
    private double PricePerSeat;
    private int NumberOfCustomer = 0;
    public int getNumberOfSeat() {
        return NumberOfSeat;
    }
    public void setNumberOfSeat(int numberOfSeat) {
        NumberOfSeat = numberOfSeat;
    }
    public double getPricePerSeat() {
        return PricePerSeat;
    }
    public void setPricePerSeat(double pricePerSeat) {
        PricePerSeat = pricePerSeat;
    }

    public int getNumberOfCustomer() {
        return NumberOfCustomer;
    }

    public void setNumberOfCustomer(int numberOfCustomer) {
        NumberOfCustomer = numberOfCustomer;
    }

    public Bus(String DriverID,int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license, int numberOfSeat, double pricePerSeat, int numberOfCustomer) {
        super(DriverID,distanceCover, type, length, wide, high, plateNumber, weight, status, license);
        NumberOfSeat = numberOfSeat;
        PricePerSeat = pricePerSeat;
        NumberOfCustomer = numberOfCustomer;
    }

}

