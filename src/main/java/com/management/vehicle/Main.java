package com.management.vehicle;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.role.Role;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        MapRequest mapRequest = MapRequest.getInstance();
        List<Hit> hitList = mapRequest.getCoordinateList("Hà Nội");

        for(Hit x: hitList)
        {
            System.out.println(x.getCity() + " " + x.getName() + " " + x.getCountry());
            System.out.println(x.getPoint().getLat());
            System.out.println(x.getPoint().getLng());
            System.out.println("--------------------------------------");
        }
    }
}