package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
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
import com.management.vehicle.request.FireBase;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class controller {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    FireBase connection = FireBase.getInstance();

    private double x = 0;
    private double y = 0;

    public controller() throws Exception {
    }

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

            }
            else {
                if (isDuplicated(isDuplicated(driverList, username.getText()))) {

                }
//            else if ((Objects.equals(username.getText(), "driver")) && (Objects.equals(password.getText(), "123456"))) {
//                alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setHeaderText(null);
//                alert.setContentText("Successfully Login!");
//                alert.showAndWait();
//                // HIDE YOUR LOGIN FORM
//                loginBtn.getScene().getWindow().hide();
//
//                // LINK YOUR DASHBOARD FORM
//                Parent root = FXMLLoader.load(getClass().getResource("/driver.fxml"));
//                Stage stage = new Stage();
//                Scene scene = new Scene(root);
//                stage.setTitle("New Window");
//                stage.setScene(scene);
//                stage.setResizable(false);
//                stage.show();
//                stage.setOnCloseRequest(event -> {
//                    event.consume();
//                    logOut(stage);
//                });
//
////                root.setOnMousePressed((MouseEvent e) ->{
////                    x = e.getSceneX();
////                    y = e.getSceneY();
////                });
////
////                root.setOnMouseDragged((MouseEvent e) ->{
////                    stage.setX(e.getScreenX() - x);
////                    stage.setY(e.getScreenY() - y);
////                });
//
//            }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        }
    } catch(Exception e){e.printStackTrace();}

    }
    public Boolean isDuplicated(List<Driver> list, String ID) {
        for (int i = 0; i < list.size(); i++)
            if (ID.equals(list.get(i).getId())) return true;
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
}
