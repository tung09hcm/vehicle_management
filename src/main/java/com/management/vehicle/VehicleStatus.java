package com.management.vehicle;
public enum VehicleStatus {
    NONE("none"),      // Giá trị 1
    ON_DUTY("on duty"), // Giá trị 2
    ON_LEAVE("on-leave"); // Giá trị 3

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
