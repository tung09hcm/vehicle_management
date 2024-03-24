package Driver;
import Trip.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private String name;
    private String phoneNumber;
    private String address;

    public List<License> getLicense() {
        return license;
    }

    public void setLicense(List<License> license) {
        this.license = license;
    }

    private List<License> license;
    private DriverStatus status;
    private List<Trip> history;

    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public DriverStatus getStatus() {
        return status;
    }
    public List<Trip> getHistory() {
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

    public void setStatus(DriverStatus status) {
        this.status = status;
    }
    public void setHistory(List<Trip> history) {
        this.history = history;
    }

    public Driver() {
        this.name = "";
        this.phoneNumber = "";
        this.address = "";
        this.license = new ArrayList<>();
        this.status = DriverStatus.NONE;
        this.history = new ArrayList<>();
    }
    public Driver(String name, String phoneNumber, String address, List<License> license, DriverStatus status, List<Trip> history) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.license = license;
        this.status = status;
        this.history = history;
    }
}
