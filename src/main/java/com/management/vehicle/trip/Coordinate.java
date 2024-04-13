package com.management.vehicle.trip;

import com.google.gson.Gson;
import com.management.vehicle.request.struct.GeocodingResponse;
import com.management.vehicle.request.struct.Hit;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.management.vehicle.request.struct.*;
public class Coordinate {
    private double lng;
    private double lat;
    public Coordinate() {
        lng = 0;
        lat = 0;
    }
    public Coordinate(double x, double y) {
        this.lng = x;
        this.lat = y;
    }

    public double getLng() {
        return lng;
    }
    public double getLat() {
        return lat;
    }
    public void setLng(double x) {
        this.lng = x;
    }
    public void setLat(double y) {
        this.lat = y;
    }

    public List<Double> getList() {
        return List.of(lng, lat);
    }
}
