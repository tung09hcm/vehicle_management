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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FireBase {
    private static FireBase instance;
    private final List<Driver> driverList = new ArrayList<>();
    private final List<Vehicle> vehicleList = new ArrayList<>();

    /**
     * Private constructor to initialize the Firebase connection.
     * It reads the token from a file, decrypts it and uses it to authenticate with Firebase.
     * @throws Exception if there's an error during the decryption or Firebase initialization.
     */
    private FireBase() throws Exception {
        byte[] key1 = {0x45, 0x47, 0x57, 0x47, 0x74, 0x51, 0x4f, 0x51, 0x4d, 0x73, 0x70, 0x55, 0x73, 0x76, 0x5a, 0x6c, 0x4a, 0x50, 0x4b, 0x5a, 0x7a, 0x71, 0x36, 0x42, 0x59, 0x37, 0x35, 0x4c, 0x69, 0x6a, 0x45, 0x48};
        byte[] key2 = {0x4e, 0x48, 0x43, 0x72, 0x77, 0x7a, 0x6e, 0x61, 0x6b, 0x45, 0x76, 0x63, 0x5a, 0x76, 0x6c, 0x70, 0x35, 0x31, 0x4d, 0x59, 0x45, 0x38, 0x7a, 0x31, 0x54, 0x7a, 0x39, 0x38, 0x6f, 0x32, 0x45, 0x6d};
        byte[] token = readFromFile("src/main/java/com/management/vehicle/request/token");
        byte[] decrypted = decrypt(token, key1, key2);
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
//        fb.getAllDriver();
//        System.out.println(fb.driverList.size());
//        for (Driver driver : fb.driverList) {
//            System.out.println(driver.getName());
//        }
//        System.out.println("Done");
//
//        Driver driver = fb.driverList.get(0);
//        System.out.println(driver.getName());
//        driver.setRecentPlateNumber("60B1 12435");
//        fb.editDriver(driver);

        fb.getAllDriver();
        for (Driver driver : fb.driverList) {
            System.out.println(driver.toString());
        }

        fb.getAllVehicle();
        for (Vehicle vehicle : fb.vehicleList) {
            System.out.println(vehicle.toString());
        }
    }

    /**
     * Encrypts the given data using AES encryption and the provided keys.
     * @param data the data to be encrypted.
     * @param key1 the first key for the encryption.
     * @param key2 the second key for the encryption.
     * @return the encrypted data.
     * @throws Exception if there's an error during the encryption.
     */
    public byte[] encrypt(byte[] data, byte[] key1, byte[] key2) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key2, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedWithAES = Base64.getEncoder().encodeToString(cipher.doFinal(data)).getBytes();
        byte[] encryptedWithKey1 = new byte[encryptedWithAES.length];
        for (int i = 0; i < encryptedWithAES.length; i++) {
            encryptedWithKey1[i] = (byte) (encryptedWithAES[i] ^ key1[i % key1.length]);
        }

        return encryptedWithKey1;
    }

    /**
     * Decrypts the given data using AES decryption and the provided keys.
     * @param data the data to be decrypted.
     * @param key1 the first key for the decryption.
     * @param key2 the second key for the decryption.
     * @return the decrypted data.
     * @throws Exception if there's an error during the decryption.
     */
    public byte[] decrypt(byte[] data, byte[] key1, byte[] key2) throws Exception {
        byte[] decryptedWithKey1 = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            decryptedWithKey1[i] = (byte) (data[i] ^ key1[i % key1.length]);
        }
        SecretKeySpec keySpec = new SecretKeySpec(key2, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        return cipher.doFinal(Base64.getDecoder().decode(new String(decryptedWithKey1)));
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

    public void editVehiclStatus(String plateNumber, VehicleStatus status) {
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
}