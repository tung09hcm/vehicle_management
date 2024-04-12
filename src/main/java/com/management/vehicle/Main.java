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
        //fireBase.getAllDriver();

        Driver newDriver = new Driver();
        newDriver.setId("1");
        newDriver.setName("tung");
        newDriver.setPhoneNumber("123123");
        newDriver.setAddress("aaaa");
        newDriver.setStatus(DriverStatus.NONE);
        newDriver.setLicensetoken(LicenseLevel.D);
        newDriver.setExpirydate("12-02-2323");
        fireBase.addDriver(newDriver);

        fireBase.getAllDriver();
        List<Driver> ls = fireBase.getDriverList();

        for(Driver dr: ls)
        {
            System.out.println(dr.getName() + "  " + dr.getId());
        }


    }
}