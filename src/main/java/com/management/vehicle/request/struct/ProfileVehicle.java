package com.management.vehicle.request.struct;

public enum ProfileVehicle {
    CAR("car"),
    BUS("truck"),
    TRUCK("truck"),
    CONTAINER("truck");

    private final String value;
    ProfileVehicle(String car) {
        this.value = car;
    }
    public String getProfile() {
        return value;
    }
}