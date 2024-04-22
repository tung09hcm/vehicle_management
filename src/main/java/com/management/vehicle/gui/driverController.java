package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.request.FireBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class driverController implements Initializable  {
    @FXML
    private JFXButton Home;
    @FXML
    private JFXButton RequestTrip;
    @FXML
    private JFXButton LogOut;
    @FXML
    private AnchorPane HomePane;
    @FXML
    private AnchorPane TripPane;
    @FXML
    private Label Date;
    @FXML
    private Label DriverName;
    @FXML
    private Label Name;
    @FXML
    private Label Phone;
    @FXML
    private Label Address;
    @FXML
    private Label License;
    @FXML
    private Label ExpiryDate;
    @FXML
    private Label Status;
    @FXML
    private JFXButton leaveRequestButton;
    @FXML
    private JFXButton endLeaveRequestButton;

    private static Driver driver;

    public driverController() {}

    public Driver getDriver() {
        return driver;
    }

    public static void setDriver(com.management.vehicle.driver.Driver d) {
        driver = d;
    }
    public void leaveRequestCreation() {
        if (driver.getStatus() == DriverStatus.NONE) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to request a leave?");
            Optional<ButtonType> option = alert.showAndWait();
            try {
                if (option.get().equals(ButtonType.OK)) {
                    driver.setStatus(DriverStatus.ON_LEAVE);
                    FireBase.getInstance().editDriverStatus(driver.getId(), DriverStatus.ON_LEAVE);
                    setInfo();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Your request is invalid");
            alert.showAndWait();
        }
    }
    public void endLeaveRequestCreation() {
        if (driver.getStatus() == DriverStatus.ON_LEAVE) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận thông tin");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to end a leave?");
            Optional<ButtonType> option = alert.showAndWait();
            try {
                if (option.get().equals(ButtonType.OK)) {
                    driver.setStatus(DriverStatus.NONE);
                    FireBase.getInstance().editDriverStatus(driver.getId(), DriverStatus.NONE);
                    setInfo();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Your request is invalid");
            alert.showAndWait();
        }
    }
    public void switchForm(ActionEvent e){
        if (e.getSource()==Home){
            HomePane.setVisible(true);
            TripPane.setVisible(false);
            setDate();
            setInfo();
        }
        else if(e.getSource()==RequestTrip) {
            HomePane.setVisible(false);
            TripPane.setVisible(true);
        }
        else {
            logOut();
        }
    }
    public void logOut() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to logOut?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if(option.get().equals(ButtonType.OK)) {
                LogOut.getScene().getWindow().hide();

                Stage stage = new Stage();
                Parent root1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
                stage.setTitle("Hello FX");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);
                stage.show();

            }
        } catch (Exception e) {e.printStackTrace();}
    }
    public void setInfo() {
        DriverName.setText(driver.getName());
        Name.setText(driver.getName());
        Phone.setText(driver.getPhoneNumber());
        Address.setText(driver.getAddress());
        switch (driver.getLicense().getType()) {
            case B1 -> License.setText("B1");
            case B2 -> License.setText("B2");
            case C -> License.setText("C");
            case D -> License.setText("D");
            case E -> License.setText("E");
            case F -> License.setText("F");
            case FC -> License.setText("FC");
            default -> License.setText("NONE");
        }
        ExpiryDate.setText(driver.getLicense().getExpiryDate());
        switch (driver.getStatus()) {
            case ON_DUTY -> Status.setText("ON DUTY");
            case ON_LEAVE -> Status.setText("ON LEAVE");
            default -> Status.setText("NONE");
        }
    }
    public void setDate() {
        LocalDate date = LocalDate.now();
        String Day = "";
        DayOfWeek day = date.getDayOfWeek();
        switch(day) {
            case MONDAY:
                Day += "Thứ hai";
                break;
            case TUESDAY:
                Day += "Thứ ba";
                break;
            case WEDNESDAY:
                Day += "Thứ tư";
                break;
            case THURSDAY:
                Day += "Thứ năm";
                break;
            case FRIDAY:
                Day += "Thứ sáu";
                break;
            case SATURDAY:
                Day += "Thứ bảy";
                break;
            default:
                Day += "Chủ nhật";
        }
        String DateOfMonth = Integer.toString(date.getDayOfMonth());
        String Month = Integer.toString(date.getMonthValue());
        String Year = Integer.toString(date.getYear());
        Date.setText(Day + ", ngày " + DateOfMonth + " tháng " + Month + " năm " + Year);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInfo();
        setDate();
        HomePane.setVisible(true);
        TripPane.setVisible(false);
    }
}
