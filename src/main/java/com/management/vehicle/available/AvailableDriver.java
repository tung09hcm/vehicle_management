package com.management.vehicle.available;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.request.FireBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvailableDriver {
    // trip distance đơn vị km
    public static List<Driver> getAvailableDriver(double tripdistance) throws Exception
    {
        FireBase fireBase = FireBase.getInstance();
        List<Driver> driverlist = fireBase.getDriverList();
        List<Driver> result = new ArrayList<>();
        List<Driver> subresult = new ArrayList<>();
        for(Driver x: driverlist)
        {
            if(x.getStatus() == DriverStatus.NONE)
            {
                if(tripdistance <= 500)
                {
                    result.add(x);
                }
                else if(tripdistance <= 1000)
                {
                    if(x.getDistanceCoverAll() >= 1000)
                    {
                        result.add(x);
                    }
                    else subresult.add(x);
                }
                else if(tripdistance <= 5000)
                {
                    if(x.getDistanceCoverAll() >= 10000)
                    {
                        result.add(x);
                    }
                    else subresult.add(x);
                }
                else
                {
                    if(x.getDistanceCoverAll() >= 50000)
                    {
                        result.add(x);
                    }
                    else subresult.add(x);
                }
            }
        }
        if(result.size() == 0)
        {
            if(subresult.size() != 0)
            {
                return subresult;
            }else
            {
                return result;
            }
        }
        return result;
    }
    public static ObservableList<Driver> getDriver(double tripdistance) throws Exception {
        ObservableList<Driver> driverlist_conn = FXCollections.observableArrayList();
        try
        {
            // System.out.println("SIGNAL on showDriverList() - 2.3");
//            firebase.getAllDriver();
            // System.out.println("SIGNAL on showDriverList() - 2.4");
            List<Driver> driverList = getAvailableDriver(tripdistance);
            for(Driver token: driverList)
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
}
