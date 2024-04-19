package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.trip.TripStatus;
import com.management.vehicle.vehicle.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class connection {
    FireBase instance = null;

    public static ObservableList<Driver> getDriver() throws Exception {
        FireBase firebase = FireBase.getInstance();
        // System.out.println("SIGNAL on showDriverList() - 2.1");
        ObservableList<Driver> driverlist_conn = FXCollections.observableArrayList();
        // System.out.println("SIGNAL on showDriverList() - 2.2");

        try
        {
            // System.out.println("SIGNAL on showDriverList() - 2.3");
//            firebase.getAllDriver();
            // System.out.println("SIGNAL on showDriverList() - 2.4");
            List<Driver> driverList = firebase.getDriverList();
            for(Driver token: driverList)
            {
                driverlist_conn.add(token);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return driverlist_conn;
    }
    public static ObservableList<Vehicle> getVehicle() throws Exception {
        FireBase firebase = FireBase.getInstance();
        ObservableList<Vehicle> vehiclelist_conn = FXCollections.observableArrayList();
        try
        {
            List<Vehicle> vehicleList = firebase.getVehicleList();
            for(Vehicle token: vehicleList)
            {
                vehiclelist_conn.add(token);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return vehiclelist_conn;
    }

    public static ObservableList<Trip> getOnDutyTrip() throws Exception {
        FireBase firebase = FireBase.getInstance();
        ObservableList<Trip> triplist_conn = FXCollections.observableArrayList();
        try
        {
            List<Trip> tripList = firebase.getListTripOnDuty();
            for(Trip token: tripList)
            {
                triplist_conn.add(token);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return triplist_conn;
    }

}
