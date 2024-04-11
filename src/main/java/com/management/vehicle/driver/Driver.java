package com.management.vehicle.driver;
import com.management.vehicle.license.License;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append("--------------------------\n");
        buffer.append("name: ").append(name).append("\n");
        buffer.append("phoneNumber: ").append(phoneNumber).append("\n");
        buffer.append("address: ").append(address).append("\n");
        buffer.append("recentPlateNumber: ").append(recentPlateNumber).append("\n");
        buffer.append("id: ").append(id).append("\n");
        buffer.append("license: ").append(license).append("\n");
        buffer.append("status: ").append(status).append("\n");
        buffer.append("history: ").append(history).append("\n");
        buffer.append("--------------------------\n");

        return buffer.toString();
    }
    private double distanceCoverAll = 0;
    private List<String> requestTrip;
    private String username;

    private String name;
    private String phoneNumber;
    private String address;
    private String recentPlateNumber;
    private String id;
    private String licensetoken;
    private String expirydate;
    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    private License license;
    private DriverStatus status;
    private List<String> history;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public String getLicensetoken() {
        return licensetoken;
    }

    public void setLicensetoken(String licensetoken) {
        this.licensetoken = licensetoken;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public DriverStatus getStatus() {
        return status;
    }
    public List<String> getHistory() {
        return history;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistanceCoverAll() {
        return distanceCoverAll;
    }

    public void setDistanceCoverAll(double distanceCoverAll) {
        this.distanceCoverAll = distanceCoverAll;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public String getRecentPlateNumber() {
        return recentPlateNumber;
    }

    public void setRecentPlateNumber(String recentPlateNumber) {
        this.recentPlateNumber = recentPlateNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
    public boolean equals(Driver driver) {
        return this.id.equals(driver.id);
    }

    public List<String> getRequestTrip() {
        return requestTrip;
    }

    public void setRequestTrip(List<String> requestTrip) {
        this.requestTrip = requestTrip;
    }

    public Driver() {
        this.name = "";
        this.phoneNumber = "";
        this.address = "";
        this.license = new License();
        this.status = DriverStatus.NONE;
        this.history = new ArrayList<>();
        this.recentPlateNumber = "";
        this.id = "";
        this.history = new ArrayList<>();
        this.licensetoken = "";
        this.expirydate = "";
        this.requestTrip = new ArrayList<>();
        this.username = "";
    }

    public Driver(String name, String phoneNumber, String address, String recentPlateNumber, String id, License license, DriverStatus status, List<String> history) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.recentPlateNumber = recentPlateNumber;
        this.id = id;
        this.license = license;
        this.status = status;
        this.history = history;

    }
}

