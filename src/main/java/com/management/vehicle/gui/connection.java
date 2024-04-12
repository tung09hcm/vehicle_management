package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.vehicle.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class connection {
    FireBase firbase = null;
    public static ObservableList<Driver> getDriver() throws Exception {
        FireBase firebase = FireBase.getInstance();
        // System.out.println("SIGNAL on showDriverList() - 2.1");
        ObservableList<Driver> driverlist_conn = FXCollections.observableArrayList();
        // System.out.println("SIGNAL on showDriverList() - 2.2");

        try
        {
            // System.out.println("SIGNAL on showDriverList() - 2.3");
            firebase.getAllDriver();
            // System.out.println("SIGNAL on showDriverList() - 2.4");
            List<Driver> driverList = firebase.getDriverList();
            if(driverList.size() == 0)
            {
                return driverlist_conn;
            }
            System.out.println("show number of driver: " + driverList.size());
            for(Driver token: driverList)
            {
                driverlist_conn.add(token);
                System.out.println(token.getName());
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
            firebase.getAllDriver();
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
}
