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
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        MapRequest mapRequest = MapRequest.getInstance();
        List<Hit> hitList1 = mapRequest.getCoordinateList("Hà Nội");
        List begin;
        begin = hitList1.getFirst().getPoint().getList();

        List<Hit> hitList2 = mapRequest.getCoordinateList("Thành phố Hồ Chí Minh");
        List end;
        end = hitList2.getFirst().getPoint().getList();

        System.out.println(begin.get(0)+"-"+begin.get(1));
        System.out.println(end.get(0)+"-"+end.get(1));

        RouteMatrix routeMatrix = mapRequest.getDistanceMatrix(begin,end);


    }
}