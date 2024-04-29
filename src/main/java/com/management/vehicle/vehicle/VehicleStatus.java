package com.management.vehicle.vehicle;
public enum VehicleStatus {
    NONE("none"),
    ON_DUTY("on-duty"),
    ON_LEAVE("on-leave"),
    NEED_REPAIR("need-repair");


    // Thuộc tính
    private final String status;
    // Constructor
    VehicleStatus(String status) {
        this.status = status;
    }

    // Phương thức getter để lấy chuỗi tượng trưng cho enum
    public String getStatus() {
        return status;
    }
}
