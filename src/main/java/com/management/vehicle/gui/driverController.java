package com.management.vehicle.gui;

import com.management.vehicle.available.AvailableVehicle;
import com.management.vehicle.driver.Driver;
import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.RouteMatrix;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.trip.Coordinate;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.trip.TripStatus;
import com.management.vehicle.vehicle.TypeVehicle;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;
import com.management.vehicle.vehicle.fuel;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.management.vehicle.available.AvailableVehicle.getPlateNumberVehicle;
import static com.management.vehicle.gui.dashboardController.BlankFieldAlert;

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
    @FXML private TextField beginLocationText;
    @FXML private TextField endLocationText;
    @FXML private TextField revenueText;
    @FXML private ComboBox<TypeVehicle> vehicleTypeComboBox;
    @FXML private AnchorPane busPane;
    @FXML private ComboBox<String> busPlateNumberComboBox;
    @FXML private TextField busNumChairText;
    @FXML private TextField busTicketPriceText;
    @FXML private TextField busNumCustomerText;
    @FXML private AnchorPane carPane;
    @FXML private ComboBox<String> carPlateNumberComboBox;
    @FXML private TextField carCustomerNameText;
    @FXML private TextField carPhoneNumberText;
    @FXML private TextField carCustomerIDText;
    @FXML private TextField carCustomerAddressText;
    @FXML private DatePicker carHireDatePicker;
    @FXML private DatePicker carReturnDatePicker;
    @FXML private AnchorPane containerPane;
    @FXML private ComboBox<String> containerPlateNumberComboBox;
    @FXML private TextField containerGoodsTypeText;
    @FXML private TextField containerGoodsWeightText;
    @FXML private AnchorPane truckPane;
    @FXML private ComboBox<String> truckPlateNumberComboBox;
    @FXML private TextField truckGoodsTypeText;
    @FXML private TextField truckGoodsWeightText;
    @FXML private TableView<Trip> requestTripTable;
    @FXML private TableColumn<Trip, String> requestBeginLocation;
    @FXML private TableColumn<Trip, String> requestEndLocation;
    @FXML private TableColumn<Trip, String> requestBeginDate;
    @FXML private TableColumn<Trip, String> requestEndDate;
    @FXML private TableColumn<Trip, String> requestPlateNumber;
    @FXML private TableColumn<Trip, String> requestCost;
    @FXML private TableColumn<Trip, String> requestRevenue;

    @FXML private ObservableList<TypeVehicle> vehicleTypeObservableList = FXCollections.observableArrayList(TypeVehicle.bus, TypeVehicle.car, TypeVehicle.container, TypeVehicle.truck);
    @FXML private ObservableList<String> busPlateNumberList = getPlateNumberVehicle(TypeVehicle.bus);
    @FXML private ObservableList<String> carPlateNumberList = getPlateNumberVehicle(TypeVehicle.car);
    @FXML private ObservableList<String> containerPlateNumberList = getPlateNumberVehicle(TypeVehicle.container);
    @FXML private ObservableList<String> truckPlateNumberList = getPlateNumberVehicle(TypeVehicle.truck);
    private static Driver driver;
    private ObservableList<Trip> homeTripList = FXCollections.observableArrayList();
    private ObservableList<Trip> requestTripList = FXCollections.observableArrayList();
    private Hit selectedHitBegin;
    private Hit selectedHitEnd;

    public void initSuggestedLocation(TextField text, ContextMenu menu, boolean begin) {
        text.setOnAction(event -> {
            String t = text.getText();
            menu.getItems().clear();
            try {
                MapRequest i = MapRequest.getInstance();
                List<Hit> listHit = i.getCoordinateList(t);
                for(Hit hit : listHit)
                {
                    MenuItem menuItem = new MenuItem(hit.getName() + " " + hit.getCity() + " " + hit.getCountry());
                    menuItem.setOnAction(menuEvent -> {
                        text.setText(menuItem.getText());
                        if (begin) selectedHitBegin = hit;
                        else selectedHitEnd = hit;
                    });
                    menu.getItems().add(menuItem);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            menu.show(text, text.getScene().getWindow().getX() + text.getLayoutX(),
                    text.getScene().getWindow().getY() + text.getLayoutY() + text.getHeight());
        });
    }
    public void mouseRightClickTriptoMap(){
        ContextMenu menuBegin = new ContextMenu();
        ContextMenu menuEnd = new ContextMenu();
        initSuggestedLocation(beginLocationText, menuBegin, true);
        initSuggestedLocation(endLocationText, menuEnd, false);

    }
    public driverController() throws Exception {}

    public Driver getDriver() {
        return driver;
    }

    public static void setDriver(com.management.vehicle.driver.Driver d) {
        driver = d;
    }
    public void updateTrip() throws Exception {
        homeTripList.clear();
        homeTripList = connection.getOnDutyTripConstraint(driver.getId());
        requestTripList = homeTripList;
    }
    public void homeShowTrip() throws Exception {
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
        homeTripTable.setItems(homeTripList);
    }
    public void requestShowTrip() throws Exception {
        requestBeginDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("begin_date"));
        requestEndDate.setCellValueFactory(new PropertyValueFactory<Trip, String>("end_date"));
        requestBeginLocation.setCellValueFactory(cellData -> {
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
        requestEndLocation.setCellValueFactory(cellData -> {
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
        requestPlateNumber.setCellValueFactory(new PropertyValueFactory<Trip, String>("plateNumber"));
        requestCost.setCellValueFactory(new PropertyValueFactory<Trip, String>("cost"));
        requestRevenue.setCellValueFactory(new PropertyValueFactory<Trip, String>("revenue"));
        requestTripTable.setItems(requestTripList);
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
    public void homeMouseRightClickTripToMap() {
        ContextMenu contextMenuTrip = new ContextMenu();
        MenuItem menuItemTrip1 = new MenuItem("Xem chi tiết bản đồ");
        menuItemTrip1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Trip selected = homeTripTable.getSelectionModel().getSelectedItem();
                if (selected == null) return;
                try {
                    MapRequest i = MapRequest.getInstance();
                    RouteMatrix routeMatrix = i.getDistanceMatrix(selected.getBegin().getList(), selected.getEnd().getList());
                    System.out.println("-----------");
                    mainMap.display(routeMatrix.getCoordinates());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        contextMenuTrip.getItems().add(menuItemTrip1);
        homeTripTable.setContextMenu(contextMenuTrip);
    }
    public void requestMouseRightClickTripToMap() {
        ContextMenu contextMenuTrip = new ContextMenu();
        MenuItem menuItemTrip1 = new MenuItem("Xem chi tiết bản đồ");
        menuItemTrip1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Trip selected = requestTripTable.getSelectionModel().getSelectedItem();
                if (selected == null) return;
                try {
                    MapRequest i = MapRequest.getInstance();
                    RouteMatrix routeMatrix = i.getDistanceMatrix(selected.getBegin().getList(), selected.getEnd().getList());
                    System.out.println("-----------");
                    mainMap.display(routeMatrix.getCoordinates());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        contextMenuTrip.getItems().add(menuItemTrip1);
        requestTripTable.setContextMenu(contextMenuTrip);
    }
    public String dateTimetoString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public LocalDateTime stringtoDateTime(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(s, formatter);
    }
    public void addTripRequest() throws Exception {
        if (driver.getStatus() != DriverStatus.NONE) {
            BlankFieldAlert("You are not ready to request a trip now.");
            return;
        }
        if (beginLocationText.getText().isEmpty() || endLocationText.getText().isEmpty() ||
revenueText.getText().isEmpty() || vehicleTypeComboBox.getSelectionModel().getSelectedItem() == null)
            BlankFieldAlert("Please fill all blank fields");
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.bus) {
            if (busPlateNumberComboBox.getSelectionModel().getSelectedItem() == null ||
busNumChairText.getText().isEmpty() || busTicketPriceText.getText().isEmpty() || busNumCustomerText.getText().isEmpty())
                BlankFieldAlert("Please fill all blank fields");
            else {
                Trip newTrip = new Trip();
                UUID uuid = UUID.randomUUID();
                newTrip.setTripID(uuid.toString());
                newTrip.setBeginLocation(beginLocationText.getText());
                newTrip.setEndLocation(endLocationText.getText());
                newTrip.setBegin(new Coordinate(selectedHitBegin.getPoint().getLng(), selectedHitBegin.getPoint().getLat()));
                newTrip.setEnd(new Coordinate(selectedHitEnd.getPoint().getLng(), selectedHitEnd.getPoint().getLat()));
                newTrip.setRevenue(Double.parseDouble(revenueText.getText()));
                newTrip.setDriverID(driver.getId());
                newTrip.setBegin_date(dateTimetoString(LocalDateTime.now()));
                newTrip.setPlateNumber(busPlateNumberComboBox.getValue());
                newTrip.setStatus(TripStatus.ON_DUTY);
                Vehicle v = FireBase.getInstance().getVehicle(busPlateNumberComboBox.getValue());
                List begin = selectedHitBegin.getPoint().getList();
                List end = selectedHitEnd.getPoint().getList();
                MapRequest i = MapRequest.getInstance();
                RouteMatrix routeMatrix = i.getDistanceMatrix(begin, end);
                LocalDateTime begin_dt = LocalDateTime.now();
                newTrip.setBegin_date(dateTimetoString(begin_dt));
                LocalDateTime end_dt = begin_dt.plus(Duration.ofMillis(routeMatrix.getDuration()));
                newTrip.setEnd_date(dateTimetoString(end_dt));
                double cost = v.getFuelType().getPricePerLiter()*v.getFuel_per_kilometer()*routeMatrix.getDistance()/1000;
                cost = Math.round(cost / 1000) * 1000;
                newTrip.setCost(cost);
                newTrip.setDistanceCover(routeMatrix.getDistance()/1000);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editDriverStatus(driver.getId(), DriverStatus.ON_DUTY);
                    driver.setStatus(DriverStatus.ON_DUTY);
                    fireBase.editVehicleStatus(v.getPlateNumber(), VehicleStatus.ON_DUTY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                FireBase.getInstance().addTrip(newTrip);
                homeTripList.add(newTrip);
                requestTripList.add(newTrip);
                homeShowTrip();
                requestShowTrip();
                setInfo();
            }
        }
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.car) {
            if (carPlateNumberComboBox.getSelectionModel().getSelectedItem() == null || carCustomerNameText.getText().isEmpty()
|| carPhoneNumberText.getText().isEmpty() || carCustomerIDText.getText().isEmpty() || carCustomerAddressText.getText().isEmpty()
|| carHireDatePicker.getValue() == null || carReturnDatePicker.getValue() == null)
                BlankFieldAlert("Please fill all blank fields");
            else {
                Trip newTrip = new Trip();
                UUID uuid = UUID.randomUUID();
                newTrip.setTripID(uuid.toString());
                newTrip.setBeginLocation(beginLocationText.getText());
                newTrip.setEndLocation(endLocationText.getText());
                newTrip.setBegin(new Coordinate(selectedHitBegin.getPoint().getLng(), selectedHitBegin.getPoint().getLat()));
                newTrip.setEnd(new Coordinate(selectedHitEnd.getPoint().getLng(), selectedHitEnd.getPoint().getLat()));
                newTrip.setRevenue(Double.parseDouble(revenueText.getText()));
                newTrip.setDriverID(driver.getId());
                newTrip.setBegin_date(dateTimetoString(LocalDateTime.now()));
                newTrip.setPlateNumber(carPlateNumberComboBox.getValue());
                newTrip.setStatus(TripStatus.ON_DUTY);
                Vehicle v = FireBase.getInstance().getVehicle(carPlateNumberComboBox.getValue());
                List begin = selectedHitBegin.getPoint().getList();
                List end = selectedHitEnd.getPoint().getList();
                MapRequest i = MapRequest.getInstance();
                RouteMatrix routeMatrix = i.getDistanceMatrix(begin, end);
                LocalDateTime begin_dt = LocalDateTime.now();
                newTrip.setBegin_date(dateTimetoString(begin_dt));
                LocalDateTime end_dt = begin_dt.plus(Duration.ofMillis(routeMatrix.getDuration()));
                newTrip.setEnd_date(dateTimetoString(end_dt));
                double cost = v.getFuelType().getPricePerLiter()*v.getFuel_per_kilometer()*routeMatrix.getDistance()/1000;
                cost = Math.round(cost / 1000) * 1000;
                newTrip.setCost(cost);
                newTrip.setDistanceCover(routeMatrix.getDistance()/1000);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editDriverStatus(driver.getId(), DriverStatus.ON_DUTY);
                    driver.setStatus(DriverStatus.ON_DUTY);
                    fireBase.editVehicleStatus(v.getPlateNumber(), VehicleStatus.ON_DUTY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                FireBase.getInstance().addTrip(newTrip);
                homeTripList.add(newTrip);
                requestTripList.add(newTrip);
                homeShowTrip();
                requestShowTrip();
                setInfo();
            }
        }
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.container) {
            if (containerPlateNumberComboBox.getSelectionModel().getSelectedItem() == null || containerGoodsTypeText.getText().isEmpty()
|| containerGoodsWeightText.getText().isEmpty())
                BlankFieldAlert("Please fill all blank fields");
            else {
                Trip newTrip = new Trip();
                UUID uuid = UUID.randomUUID();
                newTrip.setTripID(uuid.toString());
                newTrip.setBeginLocation(beginLocationText.getText());
                newTrip.setEndLocation(endLocationText.getText());
                newTrip.setBegin(new Coordinate(selectedHitBegin.getPoint().getLng(), selectedHitBegin.getPoint().getLat()));
                newTrip.setEnd(new Coordinate(selectedHitEnd.getPoint().getLng(), selectedHitEnd.getPoint().getLat()));
                newTrip.setRevenue(Double.parseDouble(revenueText.getText()));
                newTrip.setDriverID(driver.getId());
                newTrip.setBegin_date(dateTimetoString(LocalDateTime.now()));
                newTrip.setPlateNumber(containerPlateNumberComboBox.getValue());
                newTrip.setStatus(TripStatus.ON_DUTY);
                Vehicle v = FireBase.getInstance().getVehicle(containerPlateNumberComboBox.getValue());
                List begin = selectedHitBegin.getPoint().getList();
                List end = selectedHitEnd.getPoint().getList();
                MapRequest i = MapRequest.getInstance();
                RouteMatrix routeMatrix = i.getDistanceMatrix(begin, end);
                LocalDateTime begin_dt = LocalDateTime.now();
                newTrip.setBegin_date(dateTimetoString(begin_dt));
                LocalDateTime end_dt = begin_dt.plus(Duration.ofMillis(routeMatrix.getDuration()));
                newTrip.setEnd_date(dateTimetoString(end_dt));
                double cost = v.getFuelType().getPricePerLiter()*v.getFuel_per_kilometer()*routeMatrix.getDistance()/1000;
                cost = Math.round(cost / 1000) * 1000;
                newTrip.setCost(cost);
                newTrip.setDistanceCover(routeMatrix.getDistance()/1000);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editDriverStatus(driver.getId(), DriverStatus.ON_DUTY);
                    driver.setStatus(DriverStatus.ON_DUTY);
                    fireBase.editVehicleStatus(v.getPlateNumber(), VehicleStatus.ON_DUTY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                FireBase.getInstance().addTrip(newTrip);
                homeTripList.add(newTrip);
                requestTripList.add(newTrip);
                homeShowTrip();
                requestShowTrip();
                setInfo();
            }
        }
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.truck) {
            if (truckPlateNumberComboBox.getSelectionModel().getSelectedItem() == null || truckGoodsTypeText.getText().isEmpty()
|| truckGoodsWeightText.getText().isEmpty())
                BlankFieldAlert("Please fill all blank fields");
            else {
                Trip newTrip = new Trip();
                UUID uuid = UUID.randomUUID();
                newTrip.setTripID(uuid.toString());
                newTrip.setBeginLocation(beginLocationText.getText());
                newTrip.setEndLocation(endLocationText.getText());
                newTrip.setBegin(new Coordinate(selectedHitBegin.getPoint().getLng(), selectedHitBegin.getPoint().getLat()));
                newTrip.setEnd(new Coordinate(selectedHitEnd.getPoint().getLng(), selectedHitEnd.getPoint().getLat()));
                newTrip.setRevenue(Double.parseDouble(revenueText.getText()));
                newTrip.setDriverID(driver.getId());
                newTrip.setBegin_date(dateTimetoString(LocalDateTime.now()));
                newTrip.setPlateNumber(truckPlateNumberComboBox.getValue());
                newTrip.setStatus(TripStatus.ON_DUTY);
                Vehicle v = FireBase.getInstance().getVehicle(truckPlateNumberComboBox.getValue());
                List begin = selectedHitBegin.getPoint().getList();
                List end = selectedHitEnd.getPoint().getList();
                MapRequest i = MapRequest.getInstance();
                RouteMatrix routeMatrix = i.getDistanceMatrix(begin, end);
                LocalDateTime begin_dt = LocalDateTime.now();
                newTrip.setBegin_date(dateTimetoString(begin_dt));
                LocalDateTime end_dt = begin_dt.plus(Duration.ofMillis(routeMatrix.getDuration()));
                newTrip.setEnd_date(dateTimetoString(end_dt));
                double cost = v.getFuelType().getPricePerLiter()*v.getFuel_per_kilometer()*routeMatrix.getDistance()/1000;
                cost = Math.round(cost / 1000) * 1000;
                newTrip.setCost(cost);
                newTrip.setDistanceCover(routeMatrix.getDistance()/1000);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editDriverStatus(driver.getId(), DriverStatus.ON_DUTY);
                    driver.setStatus(DriverStatus.ON_DUTY);
                    fireBase.editVehicleStatus(v.getPlateNumber(), VehicleStatus.ON_DUTY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                FireBase.getInstance().addTrip(newTrip);
                homeTripList.add(newTrip);
                requestTripList.add(newTrip);
                homeShowTrip();
                requestShowTrip();
                setInfo();
            }
        }
        else return;
    }
    public void vehicleSelection() {
        if (vehicleTypeComboBox.getValue() == TypeVehicle.bus) {
            busPane.setVisible(true);
            carPane.setVisible(false);
            containerPane.setVisible(false);
            truckPane.setVisible(false);
        }
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.car) {
            busPane.setVisible(false);
            carPane.setVisible(true);
            containerPane.setVisible(false);
            truckPane.setVisible(false);
        }
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.container) {
            busPane.setVisible(false);
            carPane.setVisible(false);
            containerPane.setVisible(true);
            truckPane.setVisible(false);
        }
        else if (vehicleTypeComboBox.getValue() == TypeVehicle.truck) {
            busPane.setVisible(false);
            carPane.setVisible(false);
            containerPane.setVisible(false);
            truckPane.setVisible(true);
        }
        else {
            busPane.setVisible(false);
            carPane.setVisible(false);
            containerPane.setVisible(false);
            truckPane.setVisible(false);
        }
    }
    public void switchForm(ActionEvent e) throws Exception {
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
    public void setInfo() throws Exception {
        FireBase fireBase = FireBase.getInstance();
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
        if (driver.getStatus() == DriverStatus.ON_LEAVE) {
            Status.setText("ON LEAVE");
            fireBase.editDriverStatus(driver.getId(), DriverStatus.ON_LEAVE);
            return;
        }
        DriverStatus status = DriverStatus.NONE;
        for (Trip trip: homeTripList) {
            if (!LocalDateTime.now().isAfter(stringtoDateTime(trip.getEnd_date()))) {
                Status.setText("ON DUTY");
                fireBase.editDriverStatus(driver.getId(), DriverStatus.ON_DUTY);
                return;
            }
        }
        Status.setText("NONE");
        fireBase.editDriverStatus(driver.getId(), DriverStatus.NONE);
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
        setDate();
        HomePane.setVisible(true);
        TripPane.setVisible(false);
        busPane.setVisible(false);
        carPane.setVisible(false);
        containerPane.setVisible(false);
        truckPane.setVisible(false);
        vehicleTypeComboBox.setItems(vehicleTypeObservableList);
        busPlateNumberComboBox.setItems(busPlateNumberList);
        carPlateNumberComboBox.setItems(carPlateNumberList);
        containerPlateNumberComboBox.setItems(containerPlateNumberList);
        truckPlateNumberComboBox.setItems(truckPlateNumberList);

        try {
            updateTrip();
            homeShowTrip();
            requestShowTrip();
        } catch (Exception e) {
            System.out.println("error on loading trip");
            throw new RuntimeException(e);
        }
        try {
            setInfo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.mouseRightClickTriptoMap();
        this.homeMouseRightClickTripToMap();
        this.requestMouseRightClickTripToMap();
    }
}