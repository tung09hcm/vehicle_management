package com.management.vehicle.request;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.vehicle.TypeVehicle;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;
import com.management.vehicle.role.Role;

import java.awt.desktop.SystemSleepEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FireBase {
    private static FireBase instance;
    private final Security security = new Security();
    private final List<Driver> driverList = new ArrayList<>();
    private final List<Vehicle> vehicleList = new ArrayList<>();

    /**
     * Private constructor to initialize the Firebase connection.
     * It reads the token from a file, decrypts it and uses it to authenticate with Firebase.
     * @throws Exception if there's an error during the decryption or Firebase initialization.
     */
    private FireBase() throws Exception {
        byte[] token = readFromFile("src/main/java/com/management/vehicle/request/token");
        byte[] decrypted = security.decrypt(token);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decrypted));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setDatabaseUrl("https://vehicle-manager-hcmut-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        FirebaseApp.initializeApp(options);
    }

    /**
     * Singleton pattern to get the instance of the FireBase class.
     * @return the instance of the FireBase class.
     * @throws Exception if there's an error during the initialization.
     */
    public static FireBase getInstance() throws Exception {
        if (instance == null) {
            instance = new FireBase();
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        FireBase fb = FireBase.getInstance();
        fb.getAllVehicle();
        for (Vehicle vehicle : fb.vehicleList) {
            System.out.println(vehicle.toString());
        }
        if (fb.login("admin", "Admin@!124834ksdfgrei") == Role.ADMIN) {
            System.out.println("Login successfully");
        } else {
            System.out.println("Login failed");
        }
        System.out.println(fb.security.bytesToHex(fb.security.encrypt("1f717283-be2f-4b3c-a647-b9e3e26567ff".getBytes())));
    }

    /**
     * Reads the content of a file and returns it as a byte array.
     * @param fileName the name of the file to be read.
     * @return the content of the file as a byte array.
     * @throws IOException if there's an error during the file reading.
     */
    public byte[] readFromFile(String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileName);
        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
        return data;
    }

    /**
     * Retrieves all drivers from Firebase and stores them in the driverList.
     */
    public void getAllDriver() {
        if (!driverList.isEmpty()) { return; }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Driver");
        CompletableFuture<Void> future = new CompletableFuture<>();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildAdded");
                Driver driver = dataSnapshot.getValue(Driver.class);
                driverList.add(driver);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildChanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("onChildRemoved");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildMoved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                future.complete(null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        future.join();
    }

    Driver getDriver(String id) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Driver").child(id);
        CompletableFuture<Driver> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                Driver driver = dataSnapshot.getValue(Driver.class);
                future.complete(driver);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future.join();
    }

    /**
     * Adds a driver to Firebase.
     * @param driver The driver to be added.
     */
    public void addDriver(Driver driver) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference newDriverRef = FirebaseDatabase.getInstance().getReference("Driver").child(String.valueOf(driverList.size()));
        newDriverRef.setValue(driver, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            } else {
                System.out.println("Data saved successfully.");
                future.complete(null);
            }
        });
        future.join();
    }

    /**
     * Deletes a driver from Firebase.
     * @param id The id of the driver to be deleted.
     */
    public void deleteDriver(String id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Driver").child(id);
        updateData(future, ref);
    }

    /**
     * Edits a driver in Firebase.
     * @param driver The new driver information.
     */
    public void editDriver(Driver driver) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Driver").child(driver.getId());
        updateData(driver, future, ref);
    }

    private void editDriverAttribute(String id, String attribute, Object value) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Driver").child(id).child(attribute);
        updateData(value, future, ref);
    }

    public void editDriverName(String id, String name) {
        editDriverAttribute(id, "name", name);
    }

    public void editDriverPhoneNumber(String id, String phoneNumber) {
        editDriverAttribute(id, "phoneNumber", phoneNumber);
    }

    public void editDriverAddress(String id, String address) {
        editDriverAttribute(id, "address", address);
    }

    public void editDriverStatus(String id, DriverStatus status) {
        editDriverAttribute(id, "status", status);
    }

    public void editDriverRecentPlateNumber(String id, String recentPlateNumber) {
        editDriverAttribute(id, "recentPlateNumber", recentPlateNumber);
    }

    public void editDriverLicense(String id, List<License> license) {
        editDriverAttribute(id, "license", license);
    }




    /**
     * Retrieves all vehicles from Firebase and stores them in the vehicleList.
     */
    public void getAllVehicle() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicle");
        CompletableFuture<Void> future = new CompletableFuture<>();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildAdded");
                Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
                vehicleList.add(vehicle);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildChanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("onChildRemoved");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildMoved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                future.complete(null);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        future.join();
    }


    public void addVehicle(Vehicle vehicle) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference newVehicleRef = FirebaseDatabase.getInstance().getReference("Vehicle").child(vehicle.getPlateNumber());
        newVehicleRef.setValue(vehicle, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            } else {
                System.out.println("Data saved successfully.");
                future.complete(null);
            }
        });
        future.join();
    }

    public void deleteVehicle(String plateNumber) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicle").child(plateNumber);
        updateData(future, ref);
    }

    private void updateData(CompletableFuture<Void> future, DatabaseReference ref) {
        ref.removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be removed " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            } else {
                System.out.println("Data removed successfully.");
                future.complete(null);
            }
        });
        future.join();
    }

    public void editVehicle(String plateNumberOld, Vehicle vehicle) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (!plateNumberOld.equals(vehicle.getPlateNumber())) {
            deleteVehicle(plateNumberOld);
            addVehicle(vehicle);
            return;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicle").child(plateNumberOld);
        updateData(vehicle, future, ref);
    }

    private void editVehicleAttribute(String plateNumber, String attribute, Object value) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicle").child(plateNumber).child(attribute);
        updateData(value, future, ref);
    }

    public void editVehicleDistanceCoverFromLastRepair(String plateNumber, double distanceCoverFromLastRepair) {
        editVehicleAttribute(plateNumber, "distanceCoverFromLastRepair", distanceCoverFromLastRepair);
    }

    public void editVehicleLastRepairDate(String plateNumber, String lastRepairDate) {
        editVehicleAttribute(plateNumber, "last_repair_date", lastRepairDate);
    }

    public void editVehicleDistanceCover(String plateNumber, double distanceCover) {
        editVehicleAttribute(plateNumber, "distanceCover", distanceCover);
    }

    public void editVehicleType(String plateNumber, TypeVehicle type) {
        editVehicleAttribute(plateNumber, "type", type);
    }

    public void editVehicleLength(String plateNumber, double length) {
        editVehicleAttribute(plateNumber, "length", length);
    }

    public void editVehicleWide(String plateNumber, double wide) {
        editVehicleAttribute(plateNumber, "wide", wide);
    }

    public void editVehicleHigh(String plateNumber, double high) {
        editVehicleAttribute(plateNumber, "high", high);
    }

    public void editVehicleDriverID(String plateNumber, String driverID) {
        editVehicleAttribute(plateNumber, "driverID", driverID);
    }

    public void editVehicleWeight(String plateNumber, double weight) {
        editVehicleAttribute(plateNumber, "weight", weight);
    }

    public void editVehicleLicense(String plateNumber, LicenseLevel license) {
        editVehicleAttribute(plateNumber, "license", license);
    }

    public void editVehicleStatus(String plateNumber, VehicleStatus status) {
        editVehicleAttribute(plateNumber, "status", status);
    }

    private void updateData(Object value, CompletableFuture<Void> future, DatabaseReference ref) {
        ref.setValue(value, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be edited " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            } else {
                System.out.println("Data edited successfully.");
                future.complete(null);
            }
        });
        future.join();
    }

    public Trip getTrip(String tripID) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Trip").child(tripID);
        CompletableFuture<Trip> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                Trip trip = dataSnapshot.getValue(Trip.class);
                future.complete(trip);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future.join();
    }

    public void addTrip(Trip trip) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference newTripRef = FirebaseDatabase.getInstance().getReference("Trip").child(trip.getTripID());
        newTripRef.setValue(trip, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            } else {
                System.out.println("Data saved successfully.");
                future.complete(null);
            }
        });
        future.join();
    }

    public void addAccount(String username, String password, String role) throws Exception {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(security.bytesToHex(security.encrypt(username.getBytes()))).child(security.bytesToHex(security.encrypt(password.getBytes())));
        CompletableFuture<Void> future = new CompletableFuture<>();
        ref.setValue(role, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.out.println("Data could not be saved " + databaseError.getMessage());
                future.completeExceptionally(databaseError.toException());
            } else {
                System.out.println("Data saved successfully.");
                future.complete(null);
            }
        });
    }

    public Role login(String username, String password) throws Exception {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(security.bytesToHex(security.encrypt(username.getBytes()))).child(security.bytesToHex(security.encrypt(password.getBytes())));
        CompletableFuture<Role> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                Role role = dataSnapshot.getValue(Role.class);
                System.out.println(role);
                future.complete(role);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future.join();
    }

    public String getAPIKey() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("API").child("graphhopper");
        CompletableFuture<String> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                String key = dataSnapshot.getValue(String.class);
                try {
                    future.complete(security.byteToString(security.decrypt(security.hexToBytes(key))));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future.join();
    }
}