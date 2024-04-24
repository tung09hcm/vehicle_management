package com.management.vehicle;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.role.Role;
import com.management.vehicle.trip.Coordinate;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.vehicle.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        MapRequest mapRequest = MapRequest.getInstance();
        List<Hit> list = mapRequest.getCoordinateList("Hồ Chí Minh");
        // System.out.println(list.get(0).getCity() + list.get(0).getName() + list.get(0).getCountry());
        Coordinate coordinate1 = list.get(0).getPoint();
        String result = mapRequest.getAddressFromCoordinate(coordinate1);
        System.out.println(result);
    }
}