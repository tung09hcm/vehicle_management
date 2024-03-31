package com.management.vehicle.vehicle;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;

import java.util.List;


public class    Bus extends Vehicle {
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

    public Bus(List<String> history, String DriverID, int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, LicenseLevel license, int numberOfSeat, double pricePerSeat, int numberOfCustomer) {
        super(history,DriverID,distanceCover, type, length, wide, high, plateNumber, weight, status, license);
        NumberOfSeat = numberOfSeat;
        PricePerSeat = pricePerSeat;
        NumberOfCustomer = numberOfCustomer;
    }
    public Bus()
    {
        super();
        this.setLicense(LicenseLevel.E);
        NumberOfSeat = 0;
        PricePerSeat = 0;
        NumberOfCustomer = 0;
    }

}

