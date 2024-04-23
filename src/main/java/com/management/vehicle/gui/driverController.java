package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.RouteMatrix;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.trip.Coordinate;
import com.management.vehicle.trip.Trip;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class driverController implements Initializable  {
    @FXML private JFXButton Home;
    @FXML private JFXButton RequestTrip;
    @FXML private JFXButton LogOut;
    @FXML private AnchorPane HomePane;
    @FXML private AnchorPane TripPane;
    @FXML private Label Date;
    @FXML private Label DriverName;
    @FXML private Label Name;
    @FXML private Label Phone;
    @FXML private Label Address;
    @FXML private Label License;
    @FXML private Label ExpiryDate;
    @FXML private Label Status;
    @FXML private JFXButton leaveRequestButton;
    @FXML private JFXButton endLeaveRequestButton;
    @FXML private TableView<Trip> homeTripTable;
    @FXML private TableColumn<Trip, String> homeBeginLocation;
    @FXML private TableColumn<Trip, String> homeEndLocation;
    @FXML private TableColumn<Trip, String> homeBeginDate;
    @FXML private TableColumn<Trip, String> homeEndDate;
    @FXML private TableColumn<Trip, String> homePlateNumber;
    @FXML private TableColumn<Trip, String> homeCost;
    @FXML private TableColumn<Trip, String> homeRevenue;

    private static Driver driver;
    private ObservableList<Trip> tripList = FXCollections.observableArrayList();

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
    public void showTrip() throws Exception {
        tripList.clear();
        tripList = connection.getOnDutyTrip();
        homeBeginDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("begin_date"));
        homeEndDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("end_date"));
        homeBeginLocation.setCellValueFactory(cellData -> {
            Trip trip = cellData.getValue();
            Coordinate coord = trip.getBegin();
            String address = null;
            try {
                address = MapRequest.getInstance().getAddressFromCoordinate(coord);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return new SimpleObjectProperty<>(address);
        });
        homeEndLocation.setCellValueFactory(cellData -> {
            Trip trip = cellData.getValue();
            Coordinate coord = trip.getEnd();
            String address = null;
            try {
                address = MapRequest.getInstance().getAddressFromCoordinate(coord);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return new SimpleObjectProperty<>(address);
        });
        homePlateNumber.setCellValueFactory(new PropertyValueFactory<Trip, String>("plateNumber"));
        homeCost.setCellValueFactory(new PropertyValueFactory<Trip, String>("cost"));
        homeRevenue.setCellValueFactory(new PropertyValueFactory<Trip, String>("revenue"));
        homeTripTable.setItems(tripList);
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
//    public void initSuggestedLocation(TextField text, ContextMenu menu, boolean begin) {
//        text.setOnAction(event -> {
//            String t = text.getText();
//            menu.getItems().clear();
//            try {
//                MapRequest i = MapRequest.getInstance();
//                List<Hit> listHit = i.getCoordinateList(t);
//                for(Hit hit : listHit)
//                {
//                    MenuItem menuItem = new MenuItem(hit.getName() + " " + hit.getCity() + " " + hit.getCountry());
//                    menuItem.setOnAction(menuEvent -> {
//                        text.setText(menuItem.getText());
//                        if (begin) selectedHitBegin = hit;
//                        else selectedHitEnd = hit;
//                    });
//                    menu.getItems().add(menuItem);
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            menu.show(text, text.getScene().getWindow().getX() + text.getLayoutX(),
//                    text.getScene().getWindow().getY() + text.getLayoutY() + text.getHeight());
//        });
//    }
//    void mouseRightClickTriptoMap() {
//        ContextMenu menuBegin = new ContextMenu();
//        ContextMenu menuEnd = new ContextMenu();
//        initSuggestedLocation(beginTripText, menuBegin, true);
//        initSuggestedLocation(endTripText, menuEnd, false);
//        beginTripDatePicker.setDateTimeValue(LocalDateTime.now());
//        ContextMenu contextMenuTrip = new ContextMenu();
//        MenuItem menuItemTrip1 = new MenuItem("Xem chi tiết bản đồ");
//        menuItemTrip1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Trip selected = TripTable.getSelectionModel().getSelectedItem();
//
//                try {
//                    MapRequest i = MapRequest.getInstance();
//                    RouteMatrix routeMatrix = i.getDistanceMatrix(selected.getBegin().getList(), selected.getEnd().getList());
//                    System.out.println("-----------");
//                    mainMap.display(routeMatrix.getCoordinates());
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        contextMenuTrip.getItems().add(menuItemTrip1);
//        TripTable.setContextMenu(contextMenuTrip);
//    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInfo();
        setDate();
        HomePane.setVisible(true);
        TripPane.setVisible(false);
    }
}
