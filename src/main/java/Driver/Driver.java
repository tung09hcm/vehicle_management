package Driver;
import Trip.*;
import java.util.ArrayList;

public class Driver {
    private String name;
    private String phoneNumber;
    private String address;
    private ArrayList<License> license;
    private DriverStatus status;
    private ArrayList<Trip> history;

    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public ArrayList<License> getVector_of_license() {
        return license;
    }
    public DriverStatus getStatus() {
        return status;
    }
    public ArrayList<Trip> getHistory() {
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
    public void setVector_of_license(ArrayList<License> license) {
        this.license = license;
    }
    public void setStatus(DriverStatus status) {
        this.status = status;
    }
    public void setHistory(ArrayList<Trip> history) {
        this.history = history;
    }
    public Driver(String name, String phoneNumber, String address, ArrayList<License> license, DriverStatus status, ArrayList<Trip> history) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.license = license;
        this.status = status;
        this.history = history;
    }
}
