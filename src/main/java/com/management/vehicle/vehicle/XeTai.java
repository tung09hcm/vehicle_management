package com.management.vehicle.vehicle;
import com.management.vehicle.driver.*;


public class XeTai extends Vehicle{
    private String LoaiHanghoa;
    private double KhoiLuongHangHoa;

    public String getLoaiHanghoa() {
        return LoaiHanghoa;
    }

    public double getKhoiLuongHangHoa() {
        return KhoiLuongHangHoa;
    }

    public void setLoaiHanghoa(String loaiHanghoa) {
        LoaiHanghoa = loaiHanghoa;
    }

    public void setKhoiLuongHangHoa(double khoiLuongHangHoa) {
        KhoiLuongHangHoa = khoiLuongHangHoa;
    }

    public XeTai(int distanceCover, TypeVehicle type, double length, double wide, double high, String plateNumber, double weight, VehicleStatus status, License license, String loaiHanghoa, double khoiLuongHangHoa) {
        super(distanceCover, type, length, wide, high, plateNumber, weight, status, license);
        LoaiHanghoa = loaiHanghoa;
        KhoiLuongHangHoa = khoiLuongHangHoa;
    }

}
