package com.management.vehicle;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.role.Role;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        FireBase fireBase = FireBase.getInstance();
        fireBase.getAllDriver();
        List<Driver> ls = fireBase.getDriverList();
        for(Driver token: ls)
        {
            fireBase.deleteDriver(token.getId());
        }

        List<Driver> ls1 = fireBase.getDriverList();
        for(Driver token: ls1)
        {
            System.out.println(token.getName());
            System.out.println(token.getId());
            System.out.println("============");
        }
        System.out.println(ls1.size());
    }
}