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

        List<Driver> driverList;
        System.out.println("SIGNAL");


        Driver newDriver = new Driver();
        newDriver.setId("213123");
        newDriver.setName("tùng");
        newDriver.setPhoneNumber("0128132");
        newDriver.setAddress("aaa");
        newDriver.setStatus(DriverStatus.NONE);
        newDriver.setLicensetoken("B1");
        newDriver.setRecentPlateNumber("21A-3213");

        License license = new License();
        license.setType(LicenseLevel.D);

        newDriver.setLicense(license);

        fireBase.addDriver(newDriver);
        System.out.println("SIGNAL1");
        Driver token = fireBase.getDriver("213123");
        System.out.println(token.getName());
        fireBase.getAllDriver();
        System.out.println(fireBase.getDriverList().size());
    }
}