package com.management.vehicle.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.Optional;

public class controller {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    private double x = 0;
    private double y = 0;
    public void loginAdmin() {
        Alert alert;
        try {
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            if((Objects.equals(username.getText(), "admin")) && (Objects.equals(password.getText(), "123456"))) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login!");
                alert.showAndWait();
                // HIDE YOUR LOGIN FORM
                loginBtn.getScene().getWindow().hide();

                // LINK YOUR DASHBOARD FORM
                Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("New Window");
                stage.setScene(scene);
                stage.show();
                stage.setOnCloseRequest(event -> {
                    event.consume();
                    logOut(stage);
                });

//                root.setOnMousePressed((MouseEvent e) ->{
//                    x = e.getSceneX();
//                    y = e.getSceneY();
//                });
//
//                root.setOnMouseDragged((MouseEvent e) ->{
//                    stage.setX(e.getScreenX() - x);
//                    stage.setY(e.getScreenY() - y);
//                });

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username/Password");
                alert.showAndWait();
            }
        }
    } catch(Exception e){e.printStackTrace();}

    }
    public void logOut(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thông tin");
        alert.setHeaderText(null);
        alert.setContentText("Are you want to logOut?");
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
}
