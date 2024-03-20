package com.management.vehicle;
import java.time.LocalDate;
import Driver.License;
public class Vehicle {
    private int distance_cover;
    private String loai_xe;
    private double dai;
    private double rong;
    private double cao;
    private String bien_so_xe;
    private double trong_tai;
    private VehicleStatus status;
    private License license;


    public void setLicense(License license) {
        this.license = license;
    }
    public void setDistance_cover(int distance_cover)
    {
        this.distance_cover = distance_cover;
    }
    public void setTrong_tai(double trong_tai) {
        this.trong_tai = trong_tai;
    }
    public void setBien_so_xe(String bien_so_xe) {
        this.bien_so_xe = bien_so_xe;
    }
    public void setCao(double cao) {
        this.cao = cao;
    }
    public void setDai(double dai) {
        this.dai = dai;
    }
    public void setLoai_xe(String loai_xe) {
        this.loai_xe = loai_xe;
    }
    public void setRong(double rong) {
        this.rong = rong;
    }
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public double getCao() {
        return cao;
    }
    public double getDai() {
        return dai;
    }
    public double getRong() {
        return rong;
    }
    public double getTrong_tai() {
        return trong_tai;
    }
    public int getDistance_cover() {
        return distance_cover;
    }
    public VehicleStatus getStatus() {
        return status;
    }
    public String getBien_so_xe() {
        return bien_so_xe;
    }
    public String getLoai_xe() {
        return loai_xe;
    }
    public License getLicense() {
        return license;
    }


    public void GETHISTORY() {

    }
    public void Set_ReIMPLAINT(){}

    public Vehicle(int distance_cover, String loai_xe, double dai, double rong, double cao, String bien_so_xe, double trong_tai, VehicleStatus status) {
        this.distance_cover = distance_cover;
        this.loai_xe = loai_xe;
        this.dai = dai;
        this.rong = rong;
        this.cao = cao;
        this.bien_so_xe = bien_so_xe;
        this.trong_tai = trong_tai;
        this.status = status;
    }
}
