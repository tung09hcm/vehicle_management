package com.management.vehicle.trip;

public enum TripStatus {
    NONE("none"),

    ON_DUTY("on-duty");



    private final String status;
    TripStatus(String status)
    {
        this.status = status;
    }
    public String getStatus(){return status;}

}
