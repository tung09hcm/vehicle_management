package com.management.vehicle;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.RouteMatrix;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.role.Role;
import com.management.vehicle.trip.Coordinate;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.trip.TripStatus;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        MapRequest mapRequest = MapRequest.getInstance();
        List<Hit> list = mapRequest.getCoordinateList("hà nội");



        Coordinate coordinate1 = list.get(0).getPoint();

        System.out.println("address: " + mapRequest.getAddressFromCoordinate(coordinate1));

    }
}