package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.trip.TripStatus;
import com.management.vehicle.vehicle.*;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.*;

public class connection {
    FireBase instance = null;
    public double CalculateRevenue(double cost, Vehicle v)
    {
        if(v.getType() == TypeVehicle.car)
        {
            return cost*1.2;
        }
        else if(v.getType() == TypeVehicle.bus)
        {
            Bus bus = (Bus)v;
            return cost + ((Bus) v).getNumberOfSeat() * ((Bus) v).getPricePerSeat();
        }
        else if(v.getType() == TypeVehicle.truck)
        {
            Truck truck = (Truck)v;
            return cost*1.2 + ((Truck) v).getGoodsWeight()*5000;
        }
        else if (v.getType() == TypeVehicle.container)
        {
            Container container = (Container)v;
            return cost*1.3 + ((Container) v).getGoodsWeight()*5000;
        }
        else {
            return cost;
        }
    }
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
    public static ObservableList<Trip> getOnDutyTripConstraint(String ID) throws Exception {
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
        ObservableList<Trip> ans = FXCollections.observableArrayList();
        for (Trip trip: triplist_conn) {
            if (ID.equals(trip.getDriverID())) ans.add(trip);
        }
        return ans;
    }
}
