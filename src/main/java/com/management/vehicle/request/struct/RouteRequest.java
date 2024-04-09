package com.management.vehicle.request.struct;

import java.util.ArrayList;
import java.util.List;

public class RouteRequest {
    private String profile = "car";
    private List<List> points = new ArrayList<>();
    private String locale = "vn";
    private boolean points_encoded = false;

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setPoints(List<List> points) {
        this.points = points;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
