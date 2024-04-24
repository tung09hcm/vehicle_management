package com.management.vehicle;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.gui.VehicleManagement;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.role.Role;
import com.management.vehicle.trip.Coordinate;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.vehicle.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FireBase instance = null;
        try {
            instance = FireBase.getInstance();
            instance.getAllDriver();
            instance.getAllVehicle();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Parent root1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
        stage.setTitle("Hello FX");
        stage.setScene(new Scene(root1));
        stage.setResizable(false);

        stage.show();

    }
}