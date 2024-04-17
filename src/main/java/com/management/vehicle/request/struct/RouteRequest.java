package com.management.vehicle.request.struct;

import java.util.ArrayList;
import java.util.List;

public class RouteRequest {
    private String profile = ProfileVehicle.CAR.getProfile();
    private List<List> points = new ArrayList<>();
    private String locale = "vn";
    private final boolean points_encoded = false;

    public void setProfile(ProfileVehicle profile) {
        this.profile = profile.toString();
    }

    public void setPoints(List<List> points) {
        this.points = points;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
