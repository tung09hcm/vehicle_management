package com.management.vehicle.gui;
import com.management.vehicle.role.Role;
import com.management.vehicle.request.FireBase;

import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
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
import java.io.IOException;
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

    @FXML
    private JFXButton signUp;

    private double x = 0;
    private double y = 0;
    private final FireBase connection = FireBase.getInstance();

    public controller() throws Exception {
    }

    public void loginAdmin() throws Exception {
        Alert alert;
        try {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
//                if((Objects.equals(username.getText(), "admin")) && (Objects.equals(password.getText(), "123456"))) {
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setHeaderText(null);
//                    alert.setContentText("Successfully Login!");
//                    alert.showAndWait();
//                    // HIDE YOUR LOGIN FORM
//                    loginBtn.getScene().getWindow().hide();
//
//                    // LINK YOUR DASHBOARD FORM
//                    Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
//                    Stage stage = new Stage();
//                    Scene scene = new Scene(root);
//                    stage.setTitle("New Window");
//                    stage.setScene(scene);
//                    stage.show();
//                    stage.setOnCloseRequest(event -> {
//                        event.consume();
//                        logOut(stage);
//                    });
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
//                }
//                else {
////                    connection.getAllDriver();
////                    List<Driver> driverList = connection.getDriverList();
////                    int index = indexOf(driverList, username.getText());
////                    if (index >= 0) {
////                        if (password.getText().equals(driverList.get(index).getPassword())) {
////                            alert = new Alert(Alert.AlertType.INFORMATION);
////                            alert.setHeaderText(null);
////                            alert.setContentText("Successfully Login!");
////                            alert.showAndWait();
////                            // HIDE YOUR LOGIN FORM
////                            loginBtn.getScene().getWindow().hide();
////
////                            // LINK YOUR DASHBOARD FORM
////                            Parent root = FXMLLoader.load(getClass().getResource("/driver.fxml"));
////                            Stage stage = new Stage();
////                            Scene scene = new Scene(root);
////                            stage.setTitle("New Window");
////                            stage.setScene(scene);
////                            stage.setResizable(false);
////                            stage.show();
////                            stage.setOnCloseRequest(event -> {
////                                event.consume();
////                                logOut(stage);
////                            });
////                        }
////                        else {
////                            alert = new Alert(Alert.AlertType.ERROR);
////                            alert.setHeaderText(null);
////                            alert.setContentText("Wrong Username/Password");
////                            alert.showAndWait();
////                        }
////                    }
////                    else {
////                        alert = new Alert(Alert.AlertType.ERROR);
////                        alert.setHeaderText(null);
////                        alert.setContentText("Wrong Username/Password");
////                        alert.showAndWait();
////                    }
//                    Role role = connection.login(username.getText(), password.getText());
//                    switch (role) {
//                        case null -> {
//                            alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setHeaderText(null);
//                            alert.setContentText("Wrong Username/Password");
//                            alert.showAndWait();
//                        }
//                        case ADMIN -> {
//                            alert = new Alert(Alert.AlertType.INFORMATION);
//                            alert.setHeaderText(null);
//                            alert.setContentText("Successfully Login!");
//                            alert.showAndWait();
//                            // HIDE YOUR LOGIN FORM
//                            loginBtn.getScene().getWindow().hide();
//
//                            // LINK YOUR DASHBOARD FORM
//                            Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
//                            Stage stage = new Stage();
//                            Scene scene = new Scene(root);
//                            stage.setTitle("New Window");
//                            stage.setScene(scene);
//                            stage.show();
//                            stage.setOnCloseRequest(event -> {
//                                event.consume();
//                                logOut(stage);
//                            });
//                            break;
//                        }
//                        case USER -> {
//                            alert = new Alert(Alert.AlertType.INFORMATION);
//                            alert.setHeaderText(null);
//                            alert.setContentText("Successfully Login!");
//                            alert.showAndWait();
//                            // HIDE YOUR LOGIN FORM
//                            loginBtn.getScene().getWindow().hide();
//
//                            // LINK YOUR DASHBOARD FORM
//                            Parent root = FXMLLoader.load(getClass().getResource("/driver.fxml"));
//                            Stage stage = new Stage();
//                            Scene scene = new Scene(root);
//                            stage.setTitle("New Window");
//                            stage.setScene(scene);
//                            stage.setResizable(false);
//                            stage.show();
//                            stage.setOnCloseRequest(event -> {
//                                event.consume();
//                                logOut(stage);
//                            });
//                            break;
//                        }
//                        default -> {
//                            alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setHeaderText(null);
//                            alert.setContentText("Wrong Username/Password");
//                            alert.showAndWait();
//                        }
//                    }
//                }
//
////                else if ((Objects.equals(username.getText(), "driver")) && (Objects.equals(password.getText(), "123456"))) {
////                    alert = new Alert(Alert.AlertType.INFORMATION);
////                    alert.setHeaderText(null);
////                    alert.setContentText("Successfully Login!");
////                    alert.showAndWait();
////                    // HIDE YOUR LOGIN FORM
////                    loginBtn.getScene().getWindow().hide();
////
////                    // LINK YOUR DASHBOARD FORM
////                    Parent root = FXMLLoader.load(getClass().getResource("/driver.fxml"));
////                    Stage stage = new Stage();
////                    Scene scene = new Scene(root);
////                    stage.setTitle("New Window");
////                    stage.setScene(scene);
////                    stage.setResizable(false);
////                    stage.show();
////                    stage.setOnCloseRequest(event -> {
////                        event.consume();
////                        logOut(stage);
////                    });
////
//////                root.setOnMousePressed((MouseEvent e) ->{
//////                    x = e.getSceneX();
//////                    y = e.getSceneY();
//////                });
//////
//////                root.setOnMouseDragged((MouseEvent e) ->{
//////                    stage.setX(e.getScreenX() - x);
//////                    stage.setY(e.getScreenY() - y);
//////                });
////
////                }
////                else {
////                    alert = new Alert(Alert.AlertType.ERROR);
////                    alert.setHeaderText(null);
////                    alert.setContentText("Wrong Username/Password");
////                    alert.showAndWait();
////                }
//            }
//        } catch(Exception e){e.printStackTrace();}

                Role role = connection.login(username.getText(), password.getText());
                switch (role) {
                    case null -> {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong Username/Password");
                        alert.showAndWait();
                        break;
                    }
                    case ADMIN -> {
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
                        break;
                    }
                    case USER -> {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Login!");
                        alert.showAndWait();
                        Driver driver = connection.getDriver(username.getText());
                        if (driver != null) {
                            driverController.setDriver(driver);
                            // HIDE YOUR LOGIN FORM
                            loginBtn.getScene().getWindow().hide();

                            // LINK YOUR DASHBOARD FORM
                            Parent root = FXMLLoader.load(getClass().getResource("/driver.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle("New Window");
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();
                            stage.setOnCloseRequest(event -> {
                                event.consume();
                                logOut(stage);
                            });
                        }
                        break;
                    }
                    default -> {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong Username/Password");
                        alert.showAndWait();
                    }
                }
            }
        } catch(Exception e){e.printStackTrace();}
    }
    public int indexOf(List<Driver> list, String ID) {
        if (list.isEmpty()) return -1;
        for (int i = 0; i < list.size(); i++)
            if (ID.equals(list.get(i).getId())) return i;
        return -1;
    }
    public void setSignUp() throws IOException {
        loginBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/signUp.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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