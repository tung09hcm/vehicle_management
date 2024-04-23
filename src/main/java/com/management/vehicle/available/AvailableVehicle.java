package com.management.vehicle.available;

import com.management.vehicle.request.FireBase;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;

import java.util.ArrayList;
import java.util.List;
public class AvailableVehicle {
    public static List<Vehicle> getAvailableVehicle() throws Exception {
        List<Vehicle> vehicleList = FireBase.getInstance().getVehicleList();
        List<Vehicle> result = new ArrayList<>();
        for(Vehicle vehicle: vehicleList)
        {
            if(vehicle.getStatus() == VehicleStatus.NONE)
            {
                result.add(vehicle);
            }
        }
        return result;
    }


}
