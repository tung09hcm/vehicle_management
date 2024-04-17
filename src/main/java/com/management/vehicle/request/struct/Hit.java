package com.management.vehicle.request.struct;

import com.management.vehicle.trip.Coordinate;

public class Hit {
    Coordinate point;
    double[] extent;
    String name;
    String country;
    String countrycode;
    String city;
    String state;
    String postcode;
    String osm_value;

    public Coordinate getPoint() {
        return point;
    }

    public double[] getExtent() {
        return extent;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getOsm_value() {
        return osm_value;
    }
}
