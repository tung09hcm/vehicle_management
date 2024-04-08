package com.management.vehicle.leave;

public enum RequestLeaveStatus {
    NONE("none"),
    OVERDUE("overdue"),
    NOT_APPROVED("not-approved"),
    APPROVED("approved"),
    APPROVING("approving");
    private final String status;
    RequestLeaveStatus(String status)
    {
        this.status = status;
    }
    public String getStatus(){return status;}
}
