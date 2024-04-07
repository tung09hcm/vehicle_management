package com.management.vehicle.role;

public enum Role {
    NONE,
    USER,
    ADMIN;
    public Role getRoleFromValue(String value)
    {
        if(value.equals("Admin")) return ADMIN;
        else if(value.equals("User")) return USER;
        else return NONE;
    }
}
