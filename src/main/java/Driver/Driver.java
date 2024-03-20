package Driver;
import Trip.*;
import java.util.ArrayList;

public class Driver {
    private String name;
    private String phone_number;
    private String address;
    private ArrayList<License> vector_of_license;
    private DriverStatus status;
    private ArrayList<Trip> history;

    public String getName() {
        return name;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getAddress() {
        return address;
    }
    public ArrayList<License> getVector_of_license() {
        return vector_of_license;
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
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setVector_of_license(ArrayList<License> vector_of_license) {
        this.vector_of_license = vector_of_license;
    }
    public void setStatus(DriverStatus status) {
        this.status = status;
    }
    public void setHistory(ArrayList<Trip> history) {
        this.history = history;
    }
    public Driver(String name, String phone_number, String address, ArrayList<License> vector_of_license, DriverStatus status, ArrayList<Trip> history) {
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.vector_of_license = vector_of_license;
        this.status = status;
        this.history = history;
    }
}
