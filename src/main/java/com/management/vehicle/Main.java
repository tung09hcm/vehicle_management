package com.management.vehicle;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.role.Role;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.vehicle.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        FireBase firebase = FireBase.getInstance();
        ObservableList<Trip> triplist_conn = FXCollections.observableArrayList();
        try
        {
            System.out.println("con cuu");
            List<Trip> tripList = firebase.getListTripOnDuty();
            System.out.println("het cuu");
            for(Trip token: tripList)
            {
                triplist_conn.add(token);
                System.out.println(token.getTripID());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("size of trip = " + triplist_conn.size());
    }
}