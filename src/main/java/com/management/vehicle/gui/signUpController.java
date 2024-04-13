package com.management.vehicle.gui;

import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.License;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.role.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class signUpController {
    @FXML
    private TextField fullName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField ID;
    @FXML
    private TextField license;
    @FXML
    private Button signUp;
    @FXML
    private JFXButton login;
    @FXML
    private DatePicker issueDate;

    private final FireBase connection = FireBase.getInstance();
    public signUpController() throws Exception {
    }

    public void signUp() {
        Alert alert;
        try {
            if (ID.getText().isEmpty() || fullName.getText().isEmpty() || password.getText().isEmpty() ||
confirmPassword.getText().isEmpty() || phoneNumber.getText().isEmpty() || address.getText().isEmpty() ||
license.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else if (isDuplicated(ID.getText())) { // ID is duplicated
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The username is duplicated");
                alert.showAndWait();
            }
            else if (!password.getText().equals(password.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Password didn't match");
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Sign up successfully!");
                alert.showAndWait();

                connection.addAccount(ID.getText(), password.getText(), Role.USER);
                Driver driver = new Driver();
                driver.setId(ID.getText());
                driver.setName(fullName.getText());
                driver.setPhoneNumber(phoneNumber.getText());
                driver.setAddress(address.getText());
                License license1 = new License();
                LicenseLevel level;
                switch (license.getText()) {
                    case "B1" -> {
                        level = LicenseLevel.B1;
                        break;
                    }
                    case "B2" -> {
                        level = LicenseLevel.B2;
                        break;
                    }
                    case "C" -> {
                        level = LicenseLevel.C;
                        break;
                    }
                    case "D" -> {
                        level = LicenseLevel.D;
                        break;
                    }
                    case "E" -> {
                        level = LicenseLevel.E;
                        break;
                    }
                    case "F" -> {
                        level = LicenseLevel.F;
                        break;
                    }
                    case "FC" -> {
                        level = LicenseLevel.FC;
                        break;
                    }
                    default -> level = LicenseLevel.NONE;
                }
                license1.setType(level);
                LocalDate localDate1 = issueDate.getValue();
                LocalDate localDate2 = localDate1.plusYears(5);
                String pattern = "dd-MM-yyyy";
                String issueDatePattern = localDate1.format(DateTimeFormatter.ofPattern(pattern));
                license1.setIssueDate(issueDatePattern);
                String expiryDatePattern = localDate2.format(DateTimeFormatter.ofPattern(pattern));
                license1.setExpiryDate(expiryDatePattern);
                driver.setLicense(license1);
                driver.setStatus(DriverStatus.NONE);
                connection.addDriver(driver);
                driverController.setDriver(driver);

                // HIDE YOUR SIGNUP FORM
                signUp.getScene().getWindow().hide();

                // LINK YOUR DRIVER FORM
                Parent root = FXMLLoader.load(getClass().getResource("/driver.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> {
                    event.consume();
                    logOut(stage);
                });
            }
        } catch (Exception e) {e.printStackTrace();}
    }
    public Boolean isDuplicated(String ID) {
        connection.getAllDriver();
        List<Driver> driverList = connection.getDriverList();
        if (driverList == null) return false;
        for (int i = 0; i < driverList.size(); i++)
            if (ID.equals(driverList.get(i).getId())) return true;
        return false;
    }
    public void logOut(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to logOut?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if(option.get().equals(ButtonType.OK)) {
                stage.close();
//                stopTime = true;
//                loginOut.getScene().getWindow().hide();
//
//                Stage stage = new Stage();
//                Parent root1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
//                stage.setTitle("Hello FX");
//                stage.setScene(new Scene(root1));
//                stage.setResizable(false);
//                stage.show();

            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void setLogin() throws IOException {
        signUp.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
