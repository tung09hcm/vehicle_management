package com.management.vehicle.gui;

import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.request.RouteMatrix;
import com.management.vehicle.request.struct.Hit;
import com.management.vehicle.trip.Coordinate;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.trip.TripStatus;
import com.management.vehicle.vehicle.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class dashboardController implements Initializable
{
    @FXML
    private AnchorPane DriverPane;

    @FXML
    private JFXButton HomeButton;

    @FXML
    private JFXButton VehicleButton;

    @FXML
    private JFXButton DriverButton;

    @FXML
    private JFXButton TripButton;

    @FXML
    private JFXButton LogOutButton;

    @FXML
    private AnchorPane HomePane;

    @FXML
    private AnchorPane VehiclePane;

    @FXML
    private Label timeLabel;

    @FXML
    private AnchorPane StandardField;

    @FXML
    private AnchorPane TruckField;

    @FXML
    private AnchorPane ContainerField;

    @FXML
    private AnchorPane CarField;

    @FXML
    private AnchorPane BusField;

    private Boolean stopTime = false;

    @FXML
    private Label homeNumberVehicelLabel;

    @FXML
    private Label homeNumberDriverLabel;

    @FXML
    private Label homeNumberTripLabel;
    ////////////////////////////////////////////////////////////////////////

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableColumn<Vehicle, TypeVehicle> typeVehicleColumn;

    @FXML
    private TableColumn<Vehicle, String> plateNumberColumn;

    @FXML
    private TableColumn<Vehicle, LicenseLevel> licenseLevelColumn;

    @FXML
    private TableColumn<Vehicle, VehicleStatus> vehicleStatusColumn;

    @FXML
    private TableColumn<Vehicle, Double> lengthColumn;

    @FXML
    private TableColumn<Vehicle, Double> wideColumn;

    @FXML
    private TableColumn<Vehicle, Double> highColumn;

    @FXML
    private TableColumn<Vehicle, Double> weightColumn;

    @FXML
    private TableColumn<Vehicle, Double> distanceCoverColumn;

    @FXML
    private TableColumn<Vehicle, String> driverofVehicleColumn;

    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<TypeVehicle> typeVehicleComboBox;

    @FXML
    ObservableList<TypeVehicle> typeVehicleObservableList = FXCollections.observableArrayList(TypeVehicle.bus,TypeVehicle.car,TypeVehicle.truck,TypeVehicle.container);

    @FXML
    private TextField plateNumberText;

    @FXML
    private ComboBox<LicenseLevel> licenseLevelComboBox;

    @FXML
    ObservableList<LicenseLevel> licenseLevelObservableList = FXCollections.observableArrayList(LicenseLevel.NONE,LicenseLevel.B1,LicenseLevel.B2,LicenseLevel.C,LicenseLevel.D,LicenseLevel.E,LicenseLevel.F);

    @FXML
    private ComboBox<VehicleStatus> vehicleStatusComboBox;

    @FXML
    ObservableList<VehicleStatus> vehicleStatusObservableList = FXCollections.observableArrayList(VehicleStatus.NONE,VehicleStatus.ON_DUTY,VehicleStatus.ON_LEAVE);

    @FXML
    private TextField lengthText;

    @FXML
    private TextField wideText;

    @FXML
    private TextField highText;

    @FXML
    private TextField weightText;

    @FXML
    private TextField distanceCoverText;

    @FXML
    private TextField driverofVehicleText;

    @FXML
    private TextField vehicleHistoryText;

    @FXML
    private TextField searchVehicleText;

    @FXML
    private TextField truckGoodTypeText;

    @FXML
    private TextField truckGoodWeightText;

    @FXML
    private TextField busNumCustomerText;

    @FXML
    private TextField busNumSeatText;

    @FXML
    private TextField busPriceText;

    @FXML
    private TextField carCustomerAddressText;

    @FXML
    private TextField carCustomerIDText;

    @FXML
    private TextField carCustomerNameText;

    @FXML
    private TextField carCustomerPhonenumText;

    @FXML
    private DatePicker carRentalDatePicker;

    @FXML
    private DatePicker carReturnDatePicker;

    @FXML
    private TextField containerGoodTypeText;

    @FXML
    private TextField containerGoodWeightText;

/////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField plateNumberTripText;

    @FXML
    private TextField endTripText;

    @FXML
    private DateTimePicker beginTripDatePicker;

    @FXML
    private TextField endTripDatePicker;

    @FXML
    private TextField beginTripText;

    @FXML
    private ComboBox<fuel> fuelTripComboBox;

    ObservableList<fuel> fuelTripObservableList = FXCollections.observableArrayList(fuel.DIESEL,fuel.RON95,fuel.RON97);

    @FXML
    private TextField driverIDTripText;

    @FXML
    private TextField revenueText;

    @FXML
    private TextField costText;

    @FXML
    private TextField distanceCoverTripText;

    @FXML
    private AnchorPane TripPane;

    @FXML
    private TableView<Trip> TripTable;

    @FXML
    private TableColumn<Trip, String> beginDateTripColumn;

    @FXML
    private TableColumn<Trip, String> costTripColumn;

    @FXML
    private TableColumn<Trip, String> beginTripColumn;

    @FXML
    private TableColumn<Trip, String> driverIDTripColumn;

    @FXML
    private TableColumn<Trip, String> endTripColumn;

    @FXML
    private TableColumn<Trip, String> plateNumberTripColumn;

    @FXML
    private TableColumn<Trip, String> endDateTripColumn;

    @FXML
    private TableColumn<Trip, String> revenueTripColumn;

    private ObservableList<Trip> tripList = FXCollections.observableArrayList();

    private Hit selectedHitBegin;

    private Hit selectedHitEnd;
/////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableView<Driver> TableListDriver;

    @FXML
    private JFXButton addDriverButton;

    @FXML
    private TableColumn<Driver, String> addressDiverCol;

    @FXML
    private TextField addressDriverText;

    @FXML
    private JFXButton clearFilledButton;

    @FXML
    private TableColumn<Driver, String> driverIDCol;

    @FXML
    private TextField driverIDText;

    @FXML
    private TableColumn<Driver, String> expireDateCol;

    @FXML
    private TextField issueDateText;

    @FXML
    private TableColumn<Driver, String> licenseDriverCol;

    @FXML
    private ComboBox<LicenseLevel> licenseDriverComboBox;

    @FXML
    private TableColumn<Driver, String> nameDriverCol;

    @FXML
    private TextField nameDriverText;

    @FXML
    private TableColumn<Driver, String> phoneDriverCol;

    @FXML
    private TextField phoneDriverText;

    @FXML
    private JFXButton removeDriverButton;

    @FXML
    private TableColumn<Driver, DriverStatus> statusDriverCol;

    @FXML
    private ComboBox<DriverStatus> statusDriverComboBox;

    @FXML
    private JFXButton updateDriverButton;

    @FXML
    private DatePicker issueDatePicker;

    @FXML
    private TextField researchDriverText;

    @FXML
    private JFXButton researchDriverButton;

    ObservableList<DriverStatus> statusDriverObservableList = FXCollections.observableArrayList(DriverStatus.NONE, DriverStatus.ON_DUTY, DriverStatus.ON_LEAVE);
    ObservableList<Driver> driverList = FXCollections.observableArrayList();

/////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeVehicleComboBox.setItems(typeVehicleObservableList);
        licenseLevelComboBox.setItems(licenseLevelObservableList);
        vehicleStatusComboBox.setItems(vehicleStatusObservableList);
        statusDriverComboBox.setItems(statusDriverObservableList);
        fuelTripComboBox.setItems(fuelTripObservableList);
        ////////////////////////////////////////////////////////////////////////////
        ContextMenu contextMenuVehicle = new ContextMenu();
        MenuItem menuItemVehicle1 = new MenuItem("Xem lịch sử chuyến đi");
        menuItemVehicle1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    viewDetailInforVehicle();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        contextMenuVehicle.getItems().add(menuItemVehicle1);
        vehicleTable.setContextMenu(contextMenuVehicle);

        FilteredList<Vehicle> filteredDataVehicle = new FilteredList<>(vehicleList, b -> true);
        searchVehicleText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataVehicle.setPredicate(vehicle -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (vehicle.getPlateNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else // Does not match.
                    if (vehicle.getType().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    else return vehicle.getDriverID().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Vehicle> sortedData = new SortedList<>(filteredDataVehicle);
        sortedData.comparatorProperty().bind(vehicleTable.comparatorProperty());
        vehicleTable.setItems(sortedData);
        //////////////////////////////////////////////////////////////////////////
        ContextMenu menuBegin = new ContextMenu();
        ContextMenu menuEnd = new ContextMenu();
        initSuggestedLocation(beginTripText, menuBegin, true);
        initSuggestedLocation(endTripText, menuEnd, false);
        beginTripDatePicker.setDateTimeValue(LocalDateTime.now());
        ContextMenu contextMenuTrip = new ContextMenu();
        MenuItem menuItemTrip1 = new MenuItem("Xem chi tiết bản đồ");
        menuItemTrip1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Trip selected = TripTable.getSelectionModel().getSelectedItem();
                mainMap.display(selected.getBegin(),selected.getEnd());
            }
        });
        contextMenuTrip.getItems().add(menuItemTrip1);
        TripTable.setContextMenu(contextMenuTrip);
        //////////////////////////////////////////////////////////////////////////
        Timenow();
        try {
            showDriverList();
        } catch (Exception e) {
            System.out.println("error on loading driver list");
            throw new RuntimeException(e);
        }
        try {
            showVehicleList();
        } catch (Exception e) {
            System.out.println("error on loading vehicle list");
            throw new RuntimeException(e);
        }
        try {
            showTrip();
        } catch (Exception e) {
            System.out.println("error on loading trip");
            throw new RuntimeException(e);
        }
        showHome();
        HomePane.setVisible(true);
        VehiclePane.setVisible(false);
        DriverPane.setVisible(false);
        TripPane.setVisible(false);
    }

    public void showVehicleList() throws Exception {
        vehicleList.clear();
        vehicleList = connection.getVehicle();

        driverofVehicleColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("driverID"));
        distanceCoverColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("distanceCover"));
        typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, TypeVehicle>("type"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("length"));
        wideColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("wide"));
        highColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("high"));
        plateNumberColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("plateNumber"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("weight"));
        vehicleStatusColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, VehicleStatus>("status"));
        licenseLevelColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, LicenseLevel>("license"));

        vehicleTable.setItems(vehicleList);
    }

    public void mouseSelectedVehicle() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        distanceCoverText.setText(String.valueOf(selected.getDistanceCover()));
        typeVehicleComboBox.getSelectionModel().select(selected.getType());
        lengthText.setText(String.valueOf(selected.getLength()));
        wideText.setText(String.valueOf(selected.getWide()));
        highText.setText(String.valueOf(selected.getHigh()));
        plateNumberText.setText(selected.getPlateNumber());
        weightText.setText(String.valueOf(selected.getWeight()));
        licenseLevelComboBox.getSelectionModel().select(selected.getLicense());
        vehicleStatusComboBox.getSelectionModel().select(selected.getStatus());
        driverofVehicleText.setText(selected.getDriverID());
    }
    public boolean warningBlankFieldVehicle() {
        return distanceCoverText.getText().isEmpty()
                || typeVehicleComboBox.getSelectionModel().getSelectedItem() == null
                || lengthText.getText().isEmpty()
                || wideText.getText().isEmpty()
                || highText.getText().isEmpty()
                || plateNumberText.getText().isEmpty()
                || weightText.getText().isEmpty()
                || licenseLevelComboBox.getSelectionModel().getSelectedItem() == null
                || vehicleStatusComboBox.getSelectionModel().getSelectedItem() == null
                || driverofVehicleText.getText().isEmpty();
    }

    public void SetFieldtoVehicle(Vehicle newVehicle) {
        newVehicle.setDistanceCover(Double.parseDouble(distanceCoverText.getText()));
        newVehicle.setType(typeVehicleComboBox.getValue());
        newVehicle.setLength(Double.parseDouble(lengthText.getText()));
        newVehicle.setWide(Double.parseDouble(wideText.getText()));
        newVehicle.setHigh(Double.parseDouble(highText.getText()));
        newVehicle.setPlateNumber(plateNumberText.getText());
        newVehicle.setWeight(Double.parseDouble(weightText.getText()));
        newVehicle.setStatus(vehicleStatusComboBox.getValue());
        newVehicle.setLicense(licenseLevelComboBox.getValue());
        newVehicle.setDriverID(driverofVehicleText.getText());
    }
    public void addVehicle (ActionEvent e) throws Exception {
        for (Vehicle v : vehicleList) {
            if (Objects.equals(plateNumberText.getText(), v.getPlateNumber())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Same plate number error");
                alert.showAndWait();
                return;
            }
        }
        if (warningBlankFieldVehicle()) BlankFieldAlert("Please fill all blank fields");
        switch (typeVehicleComboBox.getValue()) {
            case TypeVehicle.car -> {
                Car newCar = new Car();
                SetFieldtoVehicle(newCar);
                FireBase fireBase = FireBase.getInstance();
                fireBase.addVehicle(newCar);
            }
            case TypeVehicle.truck -> {
                Truck newTruck = new Truck();
                SetFieldtoVehicle(newTruck);
                FireBase fireBase = FireBase.getInstance();
                fireBase.addVehicle(newTruck);
            }
            case TypeVehicle.container -> {
                Container newContainer = new Container();
                SetFieldtoVehicle(newContainer);
                FireBase fireBase = FireBase.getInstance();
                fireBase.addVehicle(newContainer);
            }
            case TypeVehicle.bus -> {
                Bus newBus = new Bus();
                SetFieldtoVehicle(newBus);
                FireBase fireBase = FireBase.getInstance();
                fireBase.addVehicle(newBus);
            }
            default -> {
            }
        }
        showVehicleList();
        resetField();
    }
    public void deleteVehicle (ActionEvent e) {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select vehicle to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You will delete this vehicle");
        alert.setContentText("Are you sure to delete this vehicle information?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    FireBase.getInstance().deleteVehicle(selected.getPlateNumber());
                    showVehicleList();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
    public void updateVehicle() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please select vehicle to update");
            alert.showAndWait();
            return;
        }
        int index = vehicleTable.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to update this vehicle information?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                switch (selected) {
                    case Car selectedCar -> {
                        SetFieldtoVehicle(selectedCar);
                    }
                    case Truck selectedTruck -> {
                        SetFieldtoVehicle(selectedTruck);
                    }
                    case Container selectedContainer -> {
                        SetFieldtoVehicle(selectedContainer);
                    }
                    case Bus selectedBus -> {
                        SetFieldtoVehicle(selectedBus);
                    }
                    default -> {
                    }
                }
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editVehicle(selected.getPlateNumber(), selected);
                    showVehicleList();
                    resetField();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
    public void viewDetailInforVehicle() throws IOException {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        vehicleHistoryInfoController.selected = selected;
        Stage newStage = new Stage();
        newStage.setTitle("History of vehicle with plate number: " + selected.getPlateNumber());
        Parent root = FXMLLoader.load(getClass().getResource("/vehicleHistoryInfo.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.show();
    }
    public void resetField() {
        distanceCoverText.setText("");
        typeVehicleComboBox.getSelectionModel().select(null);
        lengthText.setText("");
        wideText.setText("");
        highText.setText("");
        plateNumberText.setText("");
        weightText.setText("");
        licenseLevelComboBox.getSelectionModel().select(null);
        vehicleStatusComboBox.getSelectionModel().select(null);
        vehicleTable.getSelectionModel().select(null);
        driverofVehicleText.setText("");
    }
    public void MouseClicktoUnselectVehicle() {
        vehicleTable.getSelectionModel().select(null);
    }

////////////////////////////////////////////////////////

    public void showDriverList() throws Exception {
        driverList.clear();
        //System.out.println("SIGNAL on showDriverList()");

        driverList = connection.getDriver();

        driverIDCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("id"));
        nameDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("name"));
        statusDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, DriverStatus>("status"));
        phoneDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("phoneNumber"));
        addressDiverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("address"));
        licenseDriverCol.setCellValueFactory( data->
                new SimpleStringProperty(data.getValue().getLicense().getType().toString()));
        expireDateCol.setCellValueFactory(data->
                new SimpleStringProperty(data.getValue().getLicense().getExpiryDate()));

        //System.out.println("SIGNAL on showDriverList() - 3");
        TableListDriver.setItems(driverList);
        //System.out.println("SIGNAL on showDriverList() - 4");
    }

    public void addDriver() throws Exception {
        if (warningBlankFieldDriver()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            return;
        }
        for(Driver driver: driverList)
        {
            System.out.println("-----------------------------------");
            if(Objects.equals(driver.getId(), driverIDText.getText()) )
            {
                BlankFieldAlert("Same ID of driver error");
                return;
            }
            if(Objects.equals(driver.getPhoneNumber(), phoneDriverText.getText()))
            {
                BlankFieldAlert("Same phoneNumber of driver error");
                return;
            }
        }

        Driver newDriver = new Driver();
        newDriver.setId(driverIDText.getText());
        newDriver.setName(nameDriverText.getText());
        newDriver.setPhoneNumber(phoneDriverText.getText());
        newDriver.setAddress(addressDriverText.getText());
        newDriver.setStatus(statusDriverComboBox.getValue());

        LocalDate localDate = issueDatePicker.getValue();
        localDate = localDate.plusYears(5);
        String pattern = "dd-MM-yyyy";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        newDriver.getLicense().setExpiryDate(datePattern);

        FireBase.getInstance().addDriver(newDriver);

        showDriverList();

        resetFieldDriver();
    }

    public void removeDriver() throws Exception {
        Driver seleted = TableListDriver.getSelectionModel().getSelectedItem();
        if(seleted==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please select driver to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete this driver");
        alert.setContentText("You want to delete this driver");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response-> {
            if(response == ButtonType.YES) {
                try {
                    FireBase.getInstance().deleteDriver(seleted.getId());
                    showDriverList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        resetFieldDriver();
    }

    private void updateDriver() {
        Driver seleted = TableListDriver.getSelectionModel().getSelectedItem();
        if(seleted==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please seleted driver to update");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Update to driver");
        alert.setContentText("You want to update driver");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(respone->{
            if(respone==ButtonType.YES) {
                if (warningBlankFieldDriver()) {
                    BlankFieldAlert("Please fill all blank fields");
                    resetFieldDriver();
                    return;
                }
                for(Driver driver: driverList)
                {
                    if(Objects.equals(seleted, driver)) continue;
                    if(Objects.equals(driver.getId(), driverIDText.getText()) )
                    {
                        BlankFieldAlert("Same ID of driver error");
                        resetFieldDriver();
                        return;
                    }
                    //if(Objects.equals(driver.getPhoneNumber(), phoneDriverText.getText()))
                    //{
                        //BlankFieldAlert("Same phoneNumber of driver error");
                        //resetFieldDriver();
                        //return;
                    //}
                }
                seleted.setId(driverIDText.getText());
                seleted.setName(nameDriverText.getText());
                seleted.setPhoneNumber(phoneDriverText.getText());
                seleted.setAddress(addressDriverText.getText());
                seleted.setStatus(statusDriverComboBox.getValue());

                LocalDate localDate = issueDatePicker.getValue();
                String pattern = "dd-MM-yyyy";
                String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
                seleted.getLicense().setExpiryDate(datePattern);


                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editDriver(seleted);
                    showDriverList();
                    resetFieldDriver();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    public void resetFieldDriver() {
        driverIDText.setText(null);
        nameDriverText.setText(null);
        phoneDriverText.setText(null);
        addressDriverText.setText(null);
        issueDatePicker.setValue(null);
        statusDriverComboBox.getSelectionModel().select(null);
        TableListDriver.getSelectionModel().select(null);
    }

    public void mouseClicktoUnselectDriver() {
        TableListDriver.getSelectionModel().select(null);
    }
    public void mouseClicktoselecrDriver() {
        Driver selected = TableListDriver.getSelectionModel().getSelectedItem();
        if(selected==null) return;
        driverIDText.setText(selected.getId());
        nameDriverText.setText(selected.getName());
        phoneDriverText.setText(selected.getPhoneNumber());
        addressDriverText.setText(selected.getAddress());
        statusDriverComboBox.getSelectionModel().select(selected.getStatus());

        LocalDate localDate = LocalDate.parse(selected.getLicense().getExpiryDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        issueDatePicker.setValue(localDate);
    }

    public Boolean warningBlankFieldDriver() {
        return driverIDText.getText().isEmpty()
                || nameDriverText.getText().isEmpty()
                || phoneDriverText.getText().isEmpty()
                || addressDriverText.getText().isEmpty()
                || statusDriverComboBox.getSelectionModel().getSelectedItem() == null;
    }

    public static void BlankFieldAlert(String errorStr) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(errorStr);
        alert.showAndWait();
    }

    ////////////////////////////////////////////////////////
    public void controllDriver(ActionEvent e) throws Exception {
        if(e.getSource()==addDriverButton)
            addDriver();
        else if(e.getSource()==removeDriverButton)
            removeDriver();
        else if(e.getSource()==clearFilledButton)
            resetFieldDriver();
        else if(e.getSource()==updateDriverButton)
            updateDriver();
    }

    public void showHome() {
        homeNumberVehicelLabel.setText(String.valueOf(vehicleList.size()));
        homeNumberDriverLabel.setText(String.valueOf(driverList.size()));
        homeNumberTripLabel.setText(String.valueOf(tripList.size()));
    }
    /////////////////////////////////////////////////////////////////////////////////
    public void showTrip() throws Exception {
        tripList.clear();
        tripList = connection.getOnDutyTrip();
        beginDateTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("begin_date"));
        endDateTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("end_date"));
        beginTripColumn.setCellValueFactory(cellData -> {
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
        endTripColumn.setCellValueFactory(cellData -> {
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
        driverIDTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("driverID"));
        plateNumberTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("plateNumber"));
        costTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("cost"));
        revenueTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("Revenue"));
        TripTable.setItems(tripList);
    }
    public void typeVehicleChange() throws Exception {
        Vehicle v = FireBase.getInstance().getVehicle(plateNumberTripText.getText());
        if (plateNumberTripText.getText().isEmpty()) {
            StandardField.setVisible(true);
            TruckField.setVisible(false);
            ContainerField.setVisible(false);
            CarField.setVisible(false);
            BusField.setVisible(false);
        }
        else if (v.getType() == TypeVehicle.truck) {
            StandardField.setVisible(false);
            TruckField.setVisible(true);
            ContainerField.setVisible(false);
            CarField.setVisible(false);
            BusField.setVisible(false);
        }
        else if (v.getType() == TypeVehicle.container) {
            StandardField.setVisible(false);
            TruckField.setVisible(false);
            ContainerField.setVisible(true);
            CarField.setVisible(false);
            BusField.setVisible(false);
        }
        else if (v.getType() == TypeVehicle.car) {
            StandardField.setVisible(false);
            TruckField.setVisible(false);
            ContainerField.setVisible(false);
            CarField.setVisible(true);
            BusField.setVisible(false);
        }
        else {
            StandardField.setVisible(false);
            TruckField.setVisible(false);
            ContainerField.setVisible(false);
            CarField.setVisible(false);
            BusField.setVisible(true);
        }
    }
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
    public String dateTimetoString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }
    public LocalDateTime stringtoDateTime(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(s, formatter);
    }
    public void setCar(Car car) {
        car.setCustomername(carCustomerNameText.getText());
        car.setCustomerid(carCustomerIDText.getText());
        car.setCustomeraddress(carCustomerAddressText.getText());
        car.setCustomerphoneNumber(carCustomerPhonenumText.getText());

        LocalDate localDate = carRentalDatePicker.getValue();
        String pattern = "dd-MM-yyyy";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        car.setRentalDate(datePattern);
        localDate = carReturnDatePicker.getValue();
        datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        car.setReturnDate(datePattern);
    }
    public void setTruck(Truck truck) {
        truck.setGoodsType(truckGoodTypeText.getText());
        truck.setGoodsWeight(Double.parseDouble(truckGoodWeightText.getText()));
    }
    public void setContainer(Container container) {
        container.setGoodsType(containerGoodTypeText.getText());
        container.setGoodsWeight(Double.parseDouble(containerGoodWeightText.getText()));
    }
    public void setBus(Bus bus) {
        bus.setNumberOfCustomer(Integer.parseInt(busNumCustomerText.getText()));
        bus.setPricePerSeat(Double.parseDouble(busPriceText.getText()));
        bus.setNumberOfCustomer(Integer.parseInt(busNumSeatText.getText()));
    }
    public boolean warningBlankFieldTrip() {
        return beginTripText.getText().isEmpty()
                || endTripText.getText().isEmpty()
                || plateNumberTripText.getText().isEmpty()
                || driverIDTripText.getText().isEmpty()
                || fuelTripComboBox.getSelectionModel().getSelectedItem() == null;
            //doanh thu
    }
    public void updateInforTrip() throws Exception {
        if (warningBlankFieldTrip()) {
            BlankFieldAlert("Please fill all blank fields");
            return;
        }
        Vehicle v = FireBase.getInstance().getVehicle(plateNumberTripText.getText());
        Driver d = FireBase.getInstance().getDriver(driverIDTripText.getText());
        if (v == null || v.getStatus() == VehicleStatus.ON_DUTY) {
            BlankFieldAlert("Không có tt xe hoặc xe đang bận");
            return;
        }
        if (d == null || d.getStatus() == DriverStatus.ON_DUTY) {
            BlankFieldAlert("Không có tt tài xế hoặc tài xế đang bận");
            return;
        }
        switch (v) {
            case Car ignored -> {
                if (carCustomerIDText.getText().isEmpty()
                        || carCustomerPhonenumText.getText().isEmpty()
                        || carCustomerAddressText.getText().isEmpty()
                        || carCustomerNameText.getText().isEmpty()
                        || carRentalDatePicker.getValue() == null
                        || carReturnDatePicker.getValue() == null
                        || revenueText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
            }
            case Truck ignored -> {
                if (truckGoodTypeText.getText().isEmpty()
                        || truckGoodWeightText.getText().isEmpty()
                        || revenueText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
            }
            case Container ignored -> {
                if (containerGoodTypeText.getText().isEmpty()
                        || containerGoodWeightText.getText().isEmpty()
                        || revenueText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
            }
            case Bus ignored -> {
                revenueText.setEditable(false);
                if (busPriceText.getText().isEmpty()
                        || busNumSeatText.getText().isEmpty()
                        || busNumCustomerText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
                double rvnBus = Double.parseDouble(busPriceText.getText()) * Integer.parseInt(busNumCustomerText.getText());
                revenueText.setText(String.valueOf(rvnBus));
            }
            default -> {
                BlankFieldAlert("Sau khi nhập biển số, nhấn Enter để cập nhật loại xe");
                return;
            }
        }
        List begin = selectedHitBegin.getPoint().getList();
        List end = selectedHitEnd.getPoint().getList();

        MapRequest i = MapRequest.getInstance();
        RouteMatrix routeMatrix = i.getDistanceMatrix(begin, end);

        LocalDateTime begin_dt = beginTripDatePicker.getDateTimeValue();
        LocalDateTime end_dt = begin_dt.plus(Duration.ofMillis(routeMatrix.getDuration()));
        endTripDatePicker.setText(dateTimetoString(end_dt));
        double cost = fuelTripComboBox.getValue().getPricePerLiter()*v.getFuel_per_kilometer()*routeMatrix.getDistance()/1000;
        costText.setText(String.valueOf(cost));
        distanceCoverTripText.setText(String.valueOf(routeMatrix.getDistance()/1000));
    }
    public void addTrip() throws Exception {
        if (endTripDatePicker.getText().isEmpty() || costText.getText().isEmpty()) {
            BlankFieldAlert("Hãy tính toán chuyến đi trước");
            return;
        }
        Vehicle v = FireBase.getInstance().getVehicle(plateNumberTripText.getText());
        Driver d = FireBase.getInstance().getDriver(driverIDTripText.getText());

        Trip newTrip = new Trip();

        UUID uuid = UUID.randomUUID();
        newTrip.setTripID(uuid.toString());

        newTrip.setStatus(TripStatus.ON_DUTY);
        newTrip.setFuel_trip(fuelTripComboBox.getValue());
        newTrip.setDriverID(driverIDTripText.getText());
        newTrip.setPlateNumber(plateNumberTripText.getText());
        newTrip.setBegin(new Coordinate(selectedHitBegin.getPoint().getLng(), selectedHitBegin.getPoint().getLat()));
        newTrip.setEnd(new Coordinate(selectedHitEnd.getPoint().getLng(), selectedHitEnd.getPoint().getLat()));
        newTrip.setBegin_date(dateTimetoString(beginTripDatePicker.getDateTimeValue()));
        newTrip.setEnd_date(endTripDatePicker.getText());
        newTrip.setDistanceCover(Double.parseDouble(distanceCoverText.getText()));
        newTrip.setCost(Double.parseDouble(costText.getText()));
        newTrip.setRevenue(Double.parseDouble(revenueText.getText()));
        switch (v) {
            case Car selectedCar -> {
                setCar(selectedCar);
                selectedCar.setStatus(VehicleStatus.ON_DUTY);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editVehicle(v.getPlateNumber(), selectedCar);
                    showVehicleList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case Truck selectedTruck -> {
                setTruck(selectedTruck);
                selectedTruck.setStatus(VehicleStatus.ON_DUTY);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editVehicle(v.getPlateNumber(), selectedTruck);
                    showVehicleList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case Container selectedContainer -> {
                setContainer(selectedContainer);
                selectedContainer.setStatus(VehicleStatus.ON_DUTY);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editVehicle(v.getPlateNumber(), selectedContainer);
                    showVehicleList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case Bus selectedBus -> {
                setBus(selectedBus);
                selectedBus.setStatus(VehicleStatus.ON_DUTY);
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editVehicle(v.getPlateNumber(), selectedBus);
                    showVehicleList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            default ->
            {}
        }
        d.setStatus(DriverStatus.ON_DUTY);
        try {
            FireBase fireBase = FireBase.getInstance();
            fireBase.editDriverStatus(d.getId(), DriverStatus.ON_DUTY);
            showDriverList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FireBase.getInstance().addTrip(newTrip);
        showTrip();
        resetFieldTrip();
    }
    public void refreshTrip() throws Exception {
        FireBase fireBase = FireBase.getInstance();
        showTrip();
        for (Trip trip : tripList) {
            LocalDateTime dateTime = stringtoDateTime(trip.getEnd_date());
            if (dateTime.isBefore(LocalDateTime.now())) {
                Vehicle v = fireBase.getVehicle(trip.getPlateNumber());
                if (v == null) {
                    BlankFieldAlert("null vehicle????????");
                    return;
                }
                v.addTrip(trip.getTripID());
                fireBase.editVehicle(v.getPlateNumber(), v);
                fireBase.editVehicleStatus(trip.getPlateNumber(), VehicleStatus.NONE);
                showVehicleList();

                Driver d = fireBase.getDriver(trip.getDriverID());
                if (d == null) {
                    BlankFieldAlert("null driver????????");
                    return;
                }
                d.addTrip(trip.getTripID());
                fireBase.editDriver(d);
                fireBase.editDriverStatus(trip.getDriverID(), DriverStatus.NONE);
                showDriverList();

                fireBase.editStatusTrip(trip.getTripID(), TripStatus.NONE);
            }
        }
        showTrip();
        resetFieldTrip();
    }
    public void mouseSelectedTrip() throws Exception {
        Trip selected = TripTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        MapRequest mapRequest = MapRequest.getInstance();
        beginTripText.setText(mapRequest.getAddressFromCoordinate(selected.getBegin()));
        endTripText.setText(mapRequest.getAddressFromCoordinate(selected.getEnd()));
        beginTripDatePicker.setDateTimeValue(stringtoDateTime(selected.getBegin_date()));
        endTripDatePicker.setText(selected.getEnd_date());
        driverIDTripText.setText(selected.getDriverID());
        plateNumberTripText.setText(selected.getPlateNumber());
        typeVehicleChange();
        fuelTripComboBox.getSelectionModel().select(selected.getFuel_trip());
        revenueText.setText(String.valueOf(selected.getRevenue()));
        costText.setText(String.valueOf(selected.getCost()));
        distanceCoverTripText.setText(String.valueOf(selected.getDistanceCover()));
        Vehicle v = FireBase.getInstance().getVehicle(selected.getPlateNumber());
        switch (v) {
            case Car selectedCar -> {
                carCustomerNameText.setText(selectedCar.getCustomername());
                carCustomerAddressText.setText(selectedCar.getCustomeraddress());
                carCustomerIDText.setText(selectedCar.getCustomerid());
                carCustomerPhonenumText.setText(selectedCar.getCustomerphoneNumber());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                carRentalDatePicker.setValue(LocalDate.parse(selectedCar.getRentalDate(), formatter));
                carReturnDatePicker.setValue(LocalDate.parse(selectedCar.getReturnDate(), formatter));
            }
            case Truck selectedTruck -> {
                truckGoodTypeText.setText(selectedTruck.getGoodsType());
                truckGoodWeightText.setText(String.valueOf(selectedTruck.getGoodsWeight()));
            }
            case Container selectedContainer -> {
                containerGoodTypeText.setText(selectedContainer.getGoodsType());
                containerGoodWeightText.setText(String.valueOf(selectedContainer.getGoodsWeight()));
            }
            case Bus selectedBus -> {
                busNumCustomerText.setText(String.valueOf(selectedBus.getNumberOfCustomer()));
                busPriceText.setText(String.valueOf(selectedBus.getPricePerSeat()));
                busNumSeatText.setText(String.valueOf(selectedBus.getNumberOfSeat()));
            }
            default -> {}
        }
    }
    public void resetFieldTrip() {
        beginTripText.setText("");
        endTripText.setText("");
        beginTripDatePicker.setValue(null);
        endTripDatePicker.setText("");
        driverIDTripText.setText("");
        plateNumberTripText.setText("");
        fuelTripComboBox.getSelectionModel().select(null);
        revenueText.setText("");
        costText.setText("");
        distanceCoverTripText.setText("");

        truckGoodTypeText.setText("");
        truckGoodWeightText.setText("");
        carCustomerNameText.setText("");
        carCustomerAddressText.setText("");
        carCustomerIDText.setText("");
        carCustomerPhonenumText.setText("");
        carRentalDatePicker.setValue(null);
        carReturnDatePicker.setValue(null);
        containerGoodTypeText.setText("");
        containerGoodWeightText.setText("");
        busNumCustomerText.setText("");
        busPriceText.setText("");
        busNumSeatText.setText("");
    }

    public void switchForm(ActionEvent e){
        if (e.getSource()==HomeButton){
            showHome();
            HomePane.setVisible(true);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(false);
            TripPane.setVisible(false);
        }
        else if (e.getSource()==VehicleButton) {
            HomePane.setVisible(false);
            VehiclePane.setVisible(true);
            DriverPane.setVisible(false);
            TripPane.setVisible(false);
        }
        else if(e.getSource()==DriverButton) {
            HomePane.setVisible(false);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(true);
            TripPane.setVisible(false);
        }
        else if (e.getSource()==LogOutButton){
            logOut();
        }
        else {
            HomePane.setVisible(false);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(false);
            TripPane.setVisible(true);
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
                LogOutButton.getScene().getWindow().hide();

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