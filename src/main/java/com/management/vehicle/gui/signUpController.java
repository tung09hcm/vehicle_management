package com.management.vehicle.gui;

import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class signUpController {
    @FXML
    TextField username;
    @FXML
    TextField fullName;
    @FXML
    PasswordField password;
    @FXML
    PasswordField confirmPassword;
    @FXML
    TextField phoneNumber;
    @FXML
    TextField address;
    @FXML
    TextField ID;
    @FXML
    TextField license;
    @FXML
    Button signUp;
    @FXML
    JFXButton login;

    public void signUp() {
        Alert alert;
        try {
            if (username.getText().isEmpty() || fullName.getText().isEmpty() || password.getText().isEmpty() ||
confirmPassword.getText().isEmpty() || phoneNumber.getText().isEmpty() || address.getText().isEmpty() ||
ID.getText().isEmpty() || license.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else if (false) { // ID is duplicated

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
                alert.setContentText("Successful Login!");
                alert.showAndWait();
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
