package com.management.vehicle.available;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.vehicle.TypeVehicle;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
public class AvailableVehicle {
    public static List<Vehicle> getAvailableVehicle(TypeVehicle v) throws Exception {
        List<Vehicle> vehicleList = FireBase.getInstance().getVehicleList();
        List<Vehicle> result = new ArrayList<>();
        for(Vehicle vehicle: vehicleList)
        {
            if(vehicle.getStatus() == VehicleStatus.NONE && vehicle.getType() == v)
            {
                result.add(vehicle);
            }
        }
        return result;
    }
    public static ObservableList<Vehicle> getVehicle(TypeVehicle v) throws Exception {
        ObservableList<Vehicle> driverlist_conn = FXCollections.observableArrayList();
        try
        {
            // System.out.println("SIGNAL on showDriverList() - 2.3");
//            firebase.getAllDriver();
            // System.out.println("SIGNAL on showDriverList() - 2.4");
            List<Vehicle> driverList = getAvailableVehicle(v);
            for(Vehicle token: driverList)
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
    public static ObservableList<String> getPlateNumberVehicle(TypeVehicle v) throws Exception {
        ObservableList<String> driverlist_conn = FXCollections.observableArrayList();
        try
        {
            // System.out.println("SIGNAL on showDriverList() - 2.3");
//            firebase.getAllDriver();
            // System.out.println("SIGNAL on showDriverList() - 2.4");
            List<Vehicle> driverList = getAvailableVehicle(v);
            for(Vehicle token: driverList)
            {
                driverlist_conn.add(token.getPlateNumber());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return driverlist_conn;
    }
}
