package com.management.vehicle.request;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.role.Role;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.trip.TripStatus;
import com.management.vehicle.vehicle.*;

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
     *
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
     *
     * @return the instance of the FireBase class.
     * @throws Exception if there's an error during the initialization.
     */
    public static FireBase getInstance() throws Exception {
        if (instance == null) {
            instance = new FireBase();
        }
        return instance;
    }

    /**
     * Retrieves the list of Driver objects.
     * @return A list of Driver objects.
     */
    public List<Driver> getDriverList() {
        return driverList;
    }

    /**
     * Retrieves the list of Vehicle objects.
     * @return A list of Vehicle objects.
     */
    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * Reads the content of a file and returns it as a byte array.
     *
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
        if (!driverList.isEmpty()) {
            return;
        }
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
                for (Driver driver : driverList) {
                    if (driver.getId().equals(dataSnapshot.getKey())) {
                        driverList.remove(driver);
                        driverList.add(dataSnapshot.getValue(Driver.class));
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("onChildRemoved");
                for (Driver driver : driverList) {
                    if (driver.getId().equals(dataSnapshot.getKey())) {
                        driverList.remove(driver);
                        break;
                    }
                }
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

    /**
     * Retrieves a Driver object from Firebase based on the provided driver ID.
     *
     * @param id The ID of the driver to be retrieved.
     * @return The Driver object retrieved from Firebase.
     */
    public Driver getDriver(String id) {
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
     *
     * @param driver The driver to be added.
     */
    public void addDriver(Driver driver) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference newDriverRef = FirebaseDatabase.getInstance().getReference("Driver").child(String.valueOf(driver.getId()));
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
     *
     * @param id The id of the driver to be deleted.
     */
    public void deleteDriver(String id) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Driver").child(id);
        updateData(future, ref);
    }

    /**
     * Edits a driver in Firebase.
     *
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

    /**
     * Edits the name of a driver in Firebase.
     *
     * @param id   The ID of the driver.
     * @param name The new name of the driver.
     */
    public void editDriverName(String id, String name) {
        editDriverAttribute(id, "name", name);
    }

    /**
     * Edits the phone number of a driver in Firebase.
     *
     * @param id          The ID of the driver.
     * @param phoneNumber The new phone number of the driver.
     */
    public void editDriverPhoneNumber(String id, String phoneNumber) {
        editDriverAttribute(id, "phoneNumber", phoneNumber);
    }

    /**
     * Edits the address of a driver in Firebase.
     *
     * @param id      The ID of the driver.
     * @param address The new address of the driver.
     */
    public void editDriverAddress(String id, String address) {
        editDriverAttribute(id, "address", address);
    }

    /**
     * Edits the status of a driver in Firebase.
     *
     * @param id     The ID of the driver.
     * @param status The new status of the driver.
     */
    public void editDriverStatus(String id, DriverStatus status) {
        editDriverAttribute(id, "status", status);
    }

    /**
     * Edits the recent plate number of a driver in Firebase.
     *
     * @param id                The ID of the driver.
     * @param recentPlateNumber The new recent plate number of the driver.
     */
    public void editDriverRecentPlateNumber(String id, String recentPlateNumber) {
        editDriverAttribute(id, "recentPlateNumber", recentPlateNumber);
    }

    /**
     * Edits the license of a driver in Firebase.
     *
     * @param id      The ID of the driver.
     * @param license The new license of the driver.
     */
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
                Vehicle vehicle = castVehicleType(dataSnapshot);
                vehicleList.add(vehicle);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildChanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("onChildRemoved");
                for (Vehicle vehicle : vehicleList) {
                    if (vehicle.getPlateNumber().equals(dataSnapshot.getKey())) {
                        vehicleList.remove(vehicle);
                        break;
                    }
                }
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

    /**
     * Adds a new vehicle to Firebase.
     * The method takes a Vehicle object as input, then stores it in the Firebase database.
     *
     * @param vehicle The Vehicle object to be added to Firebase.
     * @throws RuntimeException If there's an error during the Firebase operation.
     */
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

    public Vehicle getVehicle(String plateNumber) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vehicle").child(plateNumber);
        CompletableFuture<Vehicle> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                Vehicle vehicle = castVehicleType(dataSnapshot);;
                future.complete(vehicle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future.join();
    }

    private Vehicle castVehicleType(DataSnapshot dataSnapshot) {
        Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
        switch (vehicle.getType()) {
            case car:
                vehicle = dataSnapshot.getValue(Car.class);
                break;
            case truck:
                vehicle = dataSnapshot.getValue(Truck.class);
                break;
            case container:
                vehicle = dataSnapshot.getValue(Container.class);
                break;
            case bus:
                vehicle = dataSnapshot.getValue(Bus.class);
                break;
            default:
                break;
        }
        return vehicle;
    }

    /**
     * Deletes a vehicle from Firebase.
     *
     * @param plateNumber The plate number of the vehicle to be deleted.
     */
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

    /**
     * Edits a vehicle in Firebase.
     * If the plate number of the vehicle has changed, the old vehicle is deleted and the new vehicle is added.
     * Otherwise, the vehicle data is updated in Firebase.
     *
     * @param plateNumberOld The old plate number of the vehicle.
     * @param vehicle        The vehicle object containing the new data.
     */
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

    /**
     * Edits the distance covered from the last repair of a vehicle in Firebase.
     *
     * @param plateNumber                 The plate number of the vehicle.
     * @param distanceCoverFromLastRepair The new distance covered from the last repair.
     */
    public void editVehicleDistanceCoverFromLastRepair(String plateNumber, double distanceCoverFromLastRepair) {
        editVehicleAttribute(plateNumber, "distanceCoverFromLastRepair", distanceCoverFromLastRepair);
    }

    /**
     * Edits the last repair date of a vehicle in Firebase.
     *
     * @param plateNumber    The plate number of the vehicle.
     * @param lastRepairDate The new last repair date.
     */
    public void editVehicleLastRepairDate(String plateNumber, String lastRepairDate) {
        editVehicleAttribute(plateNumber, "last_repair_date", lastRepairDate);
    }

    /**
     * Edits the distance covered by a vehicle in Firebase.
     *
     * @param plateNumber   The plate number of the vehicle.
     * @param distanceCover The new distance covered.
     */
    public void editVehicleDistanceCover(String plateNumber, double distanceCover) {
        editVehicleAttribute(plateNumber, "distanceCover", distanceCover);
    }

    /**
     * Edits the type of vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param type        The new type of the vehicle.
     */
    public void editVehicleType(String plateNumber, TypeVehicle type) {
        editVehicleAttribute(plateNumber, "type", type);
    }

    /**
     * Edits the length of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param length      The new length of the vehicle.
     */
    public void editVehicleLength(String plateNumber, double length) {
        editVehicleAttribute(plateNumber, "length", length);
    }

    /**
     * Edits the width of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param wide        The new width of the vehicle.
     */
    public void editVehicleWide(String plateNumber, double wide) {
        editVehicleAttribute(plateNumber, "wide", wide);
    }

    /**
     * Edits the height of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param high        The new height of the vehicle.
     */
    public void editVehicleHigh(String plateNumber, double high) {
        editVehicleAttribute(plateNumber, "high", high);
    }

    /**
     * Edits the driver ID of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param driverID    The new driver ID.
     */
    public void editVehicleDriverID(String plateNumber, String driverID) {
        editVehicleAttribute(plateNumber, "driverID", driverID);
    }

    /**
     * Edits the weight of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param weight      The new weight of the vehicle.
     */
    public void editVehicleWeight(String plateNumber, double weight) {
        editVehicleAttribute(plateNumber, "weight", weight);
    }

    /**
     * Edits the license of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param license     The new license of the vehicle.
     */
    public void editVehicleLicense(String plateNumber, LicenseLevel license) {
        editVehicleAttribute(plateNumber, "license", license);
    }

    /**
     * Edits the status of a vehicle in Firebase.
     *
     * @param plateNumber The plate number of the vehicle.
     * @param status      The new status of the vehicle.
     */
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

    /**
     * Retrieves a Trip object from Firebase based on the provided trip ID.
     *
     * @param tripID The ID of the trip to be retrieved.
     * @return The Trip object retrieved from Firebase.
     */
    public Trip getTrip(String tripID) {
        if (tripID == null) {
            throw new RuntimeException("Invalid input");
        }
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

    /**
     * Adds a new trip to Firebase.
     * The method takes a Trip object as input, then stores it in the Firebase database.
     *
     * @param trip The Trip object to be added to Firebase.
     * @throws RuntimeException If there's an error during the Firebase operation.
     */
    public void addTrip(Trip trip) {
        if (trip == null || trip.getTripID().isEmpty()) {
            throw new RuntimeException("Invalid input");
        }
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

    public List<Trip> getListTripOnDuty() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Trip");
        CompletableFuture<List<Trip>> future = new CompletableFuture<>();
        List<Trip> listTrip = new ArrayList<>();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildAdded");
                Trip trip = dataSnapshot.getValue(Trip.class);
                if (trip.getStatus() == TripStatus.ON_DUTY) {
                    listTrip.add(trip);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                System.out.println("onChildChanged");
                for (Trip trip : listTrip) {
                    if (trip.getTripID().equals(dataSnapshot.getKey())) {
                        listTrip.remove(trip);
                        listTrip.add(dataSnapshot.getValue(Trip.class));
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                System.out.println("onChildRemoved");
                for (Trip trip : listTrip) {
                    if (trip.getTripID().equals(dataSnapshot.getKey())) {
                        listTrip.remove(trip);
                        break;
                    }
                }
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
                future.complete(listTrip);
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
     * Adds a new account to Firebase.
     * The method encrypts the username and password, then stores them in the Firebase database along with the user's role.
     *
     * @param username The username of the new account.
     * @param password The password of the new account.
     * @param role     The role of the new account.
     * @throws Exception If there's an error during the encryption.
     */
    public void addAccount(String username, String password, Role role) throws Exception {
        if (role == null || username == null || password == null) {
            throw new RuntimeException("Invalid input");
        }
        if (checkAccountExist(username)) {
            throw new RuntimeException("Account already exists");
        }
        String encryptedUsername = security.bytesToHex(security.encrypt(username.getBytes()));
        String encryptedPassword = security.bytesToHex(security.encrypt(password.getBytes()));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(encryptedUsername).child(encryptedPassword);
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
        future.join();
    }

    /**
     * Authenticates a user by their username and password.
     * The method encrypts the username and password, then checks if they exist in the Firebase database.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The role of the authenticated user.
     * @throws Exception If there's an error during the encryption.
     */
    public Role login(String username, String password) throws Exception {
        if (username == null || password == null) {
            throw new RuntimeException("Invalid input");
        }
        String encryptedUsername = security.bytesToHex(security.encrypt(username.getBytes()));
        String encryptedPassword = security.bytesToHex(security.encrypt(password.getBytes()));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(encryptedUsername).child(encryptedPassword);
        CompletableFuture<Role> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                Role role = dataSnapshot.getValue(Role.class);
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

    private Boolean checkAccountExist(String username) throws Exception {
        String encryptedUsername = security.bytesToHex(security.encrypt(username.getBytes()));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(encryptedUsername);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");
                Boolean exist = dataSnapshot.exists();
                future.complete(exist);
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
     * Retrieves the API key from Firebase, decrypts it and returns it as a string.
     *
     * @return The decrypted API key as a string.
     * @throws RuntimeException If there's an error during the decryption.
     */
    public String getAPIKey(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("API").child(key);
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