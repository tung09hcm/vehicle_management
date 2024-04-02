package com.management.vehicle.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class dashboardController
{
    @FXML
    private AnchorPane DriverPane;

    @FXML
    private Button Drivers;

    @FXML
    private Button HomeButton;

    @FXML
    private AnchorPane HomePane;

    @FXML
    private AnchorPane InforVehicle;

    @FXML
    private AnchorPane VehiclePane;

    @FXML
    private Button Vehicles;

    @FXML
    private Button addVehicle;

    @FXML
    private Button loginOut;

    @FXML
    private Button removeVehicle;

    @FXML
    private Label timeLabel;

    private Boolean stopTime = false;


    public void switchForm(ActionEvent e){


        if (e.getSource()==HomeButton){
            Timenow();
            HomePane.setVisible(true);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(false);
        }
        else if (e.getSource()==Vehicles) {
            HomePane.setVisible(false);
            VehiclePane.setVisible(true);
            DriverPane.setVisible(false);
        }
        else if(e.getSource()==Drivers) {
            HomePane.setVisible(false);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(true);
        }
        else {
            logOut();
        }
    }

    public void logOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Are you want to logOut?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if(option.get().equals(ButtonType.OK)) {
                stopTime = true;
                loginOut.getScene().getWindow().hide();

                Stage stage = new Stage();
                Parent root1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
                stage.setTitle("Hello FX");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);
                stage.show();

            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void Timenow() {
        Thread thread = new Thread( () ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(!stopTime) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {e.printStackTrace();}
                final String timenow = sdf.format(new Date());
                Platform.runLater( ()-> {
                    timeLabel.setText(timenow);
                });
            }
        });
        thread.start();
    }
}
