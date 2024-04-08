package com.management.vehicle.trip;

public enum TripStatus {
    NONE("none"),
    ON_DUTY("on-duty"),
    NOT_APPROVED("not-approved"),
    APPROVED("approved"),
    APPROVING("approving");
    private final String status;
    TripStatus(String status)
    {
        this.status = status;
    }
    public String getStatus(){return status;}

}
