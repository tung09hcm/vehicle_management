package com.management.vehicle.gui;

import com.jfoenix.controls.JFXButton;
import com.management.vehicle.available.AvailableDriver;
import com.management.vehicle.available.AvailableVehicle;
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
import javafx.util.Callback;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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
    private AnchorPane StandardField;

    @FXML
    private AnchorPane TruckField;

    @FXML
    private AnchorPane ContainerField;

    @FXML
    private AnchorPane CarField;

    @FXML
    private AnchorPane BusField;

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
    private TableColumn<Vehicle, String> driverofVehicleColumn;

    @FXML
    private AnchorPane BusVehicleField;

    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    @FXML
    private ComboBox<TypeVehicle> typeVehicleComboBox;

    @FXML
    ObservableList<TypeVehicle> typeVehicleObservableList = FXCollections.observableArrayList(TypeVehicle.bus,TypeVehicle.car,TypeVehicle.truck,TypeVehicle.container);

    @FXML
    private TextField plateNumberText;

    @FXML
    private TextField lengthText;

    @FXML
    private TextField wideText;

    @FXML
    private TextField highText;

    @FXML
    private TextField weightText;

    @FXML
    private TextField searchVehicleText;

    ObservableList<LicenseLevel> licenseLevelObservableList = FXCollections.observableArrayList(LicenseLevel.NONE,LicenseLevel.B1,LicenseLevel.B2,LicenseLevel.C,LicenseLevel.D,LicenseLevel.E,LicenseLevel.F);



    ObservableList<fuel> typeFuelVehicleObservableList = FXCollections.observableArrayList(fuel.DIESEL, fuel.RON95, fuel.RON97);

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
    private TextField containerGoodTypeText;

    @FXML
    private TextField containerGoodWeightText;

/////////////////////////////////////////////////////////////////////////////////
    @FXML
    private ComboBox<TypeVehicle> typeVehicleTripComboBox;

    @FXML
    private ComboBox<Vehicle> plateNumberTripComboBox;

    @FXML
    private ComboBox<Driver> driverIDTripComboBox;

    @FXML
    private TextField endTripText;

    @FXML
    private DateTimePicker beginTripDatePicker;

    @FXML
    private TextField endTripDatePicker;

    @FXML
    private TextField beginTripText;

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

    private String durationNow;

    MapRequest mapRequest = MapRequest.getInstance();

    RouteMatrix routeMatrix;
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
    private TableColumn<Driver, String> licenseDriverCol;

    @FXML
    private TableColumn<Driver, String> plateNumberDriverCol;

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
    private JFXButton updateDriverButton;

    @FXML
    private DatePicker issueDatePicker;

    @FXML
    private TextField researchDriverText;

    ObservableList<Driver> driverList = FXCollections.observableArrayList();

    public dashboardController() throws Exception {
    }

/////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeVehicleComboBox.setItems(typeVehicleObservableList);
        licenseDriverComboBox.setItems(licenseLevelObservableList);
        //typeFuelVehicleComboBox.setItems(typeFuelVehicleObservableList);
        typeVehicleTripComboBox.setItems(typeVehicleObservableList);

        this.mouseRightClicktoHistoryInfoVehicle();
        this.mouseRightClicktoHistoryInfoDriver();
        this.mouseRightClickTriptoMap();

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

        /// check vehicle completely maintenance
        for(Vehicle vehicle: vehicleList) {
            if(vehicle.getStatus()==VehicleStatus.ON_LEAVE) {
                LocalDate lastRepairDate = LocalDate.parse(vehicle.getLast_repair_date(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate currentDate = LocalDate.now(); // Ngày hiện tại

                Period period = Period.between(lastRepairDate, currentDate);
                int years = period.getYears();
                int months = period.getMonths();
                int days = period.getDays();

                if(days>=2 || months>0 || years>0) {    /// Maintenance within 2 days
                    vehicle.setStatus(VehicleStatus.NONE);
                    vehicle.AddMaintance(vehicle.getLast_repair_date());
                    FireBase.getInstance().editVehicle(vehicle.getPlateNumber(), vehicle);
                }
            }
        }

        driverofVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("driverID"));
        typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        wideColumn.setCellValueFactory(new PropertyValueFactory<>("wide"));
        highColumn.setCellValueFactory(new PropertyValueFactory<>("high"));
        plateNumberColumn.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        vehicleStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        licenseLevelColumn.setCellValueFactory(new PropertyValueFactory<>("license"));

        vehicleTable.setItems(vehicleList);
        this.researchVehicle();
    }


    public void researchVehicle() {
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
    }

    public void mouseSelectedVehicle() {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        typeVehicleComboBox.getSelectionModel().select(selected.getType());
        lengthText.setText(String.valueOf(selected.getLength()));
        wideText.setText(String.valueOf(selected.getWide()));
        highText.setText(String.valueOf(selected.getHigh()));
        plateNumberText.setText(selected.getPlateNumber());
        weightText.setText(String.valueOf(selected.getWeight()));
        //typeFuelVehicleComboBox.getSelectionModel().select(selected.getFuel_v());
        if (selected instanceof Bus selectedBus) {
            busPriceText.setText(String.valueOf(selectedBus.getPricePerSeat()));
            busNumSeatText.setText(String.valueOf(selectedBus.getNumberOfSeat()));
        }
    }

    public boolean warningBlankFieldVehicle() {
        return typeVehicleComboBox.getSelectionModel().getSelectedItem() == null
                || lengthText.getText().isEmpty()
                || wideText.getText().isEmpty()
                || highText.getText().isEmpty()
                || plateNumberText.getText().isEmpty()
                || weightText.getText().isEmpty();
                //|| typeFuelVehicleComboBox.getSelectionModel().getSelectedItem() == null;
    }
    public static void BlankFieldVehicleAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }

    public void SetFieldtoVehicle(Vehicle newVehicle) {
        newVehicle.setType(typeVehicleComboBox.getValue());
        newVehicle.setLength(Double.parseDouble(lengthText.getText()));
        newVehicle.setWide(Double.parseDouble(wideText.getText()));
        newVehicle.setHigh(Double.parseDouble(highText.getText()));
        newVehicle.setPlateNumber(plateNumberText.getText());
        newVehicle.setWeight(Double.parseDouble(weightText.getText()));
        //newVehicle.setFuel_v(typeFuelVehicleComboBox.getValue());
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
        if (warningBlankFieldVehicle()) {
            BlankFieldAlert("Please fill all blank fields");
            return;
        }
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
                if (busNumSeatText.getText().isEmpty() || busPriceText.getText().isEmpty()) {
                    BlankFieldAlert("Vui lòng điền thông tin xe buýt");
                    return;
                }
                Bus newBus = new Bus();
                SetFieldtoVehicle(newBus);
                newBus.setNumberOfSeat(Integer.parseInt(busNumSeatText.getText()));
                newBus.setPricePerSeat(Double.parseDouble(busPriceText.getText()));
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
        if (selected.getStatus() != VehicleStatus.NONE) {
            BlankFieldAlert("Xe đang bận, không thể xóa");
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to update this vehicle information?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if(!Objects.equals(selected.getPlateNumber(), plateNumberText.getText()))
                {
                    BlankFieldAlert("Can't update Plate number");
                    resetFieldDriver();
                    return;
                }
                if(!Objects.equals(selected.getType(), typeVehicleComboBox.getSelectionModel().getSelectedItem()))
                {
                    BlankFieldAlert("Can't update type vehicle");
                    resetFieldDriver();
                    return;
                }

                if (selected instanceof Bus selectedBus) {
                    SetFieldtoVehicle(selectedBus);
                    selectedBus.setPricePerSeat(Double.parseDouble(busPriceText.getText()));
                    selectedBus.setNumberOfSeat(Integer.parseInt(busNumSeatText.getText()));
                    try {
                        FireBase fireBase = FireBase.getInstance();
                        fireBase.editVehicle(selectedBus.getPlateNumber(), selectedBus);
                        showVehicleList();
                        resetField();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    SetFieldtoVehicle(selected);
                    try {
                        FireBase fireBase = FireBase.getInstance();
                        fireBase.editVehicle(selected.getPlateNumber(), selected);
                        showVehicleList();
                        resetField();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    public void mouseRightClicktoHistoryInfoVehicle(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Xem lịch sử chuyến đi");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    viewDetailInforVehicle();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        MenuItem menuItem2 = new MenuItem("Xem lịch sử bảo dưỡng");
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    viewMaintenanceHistoryVehicle();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        vehicleTable.setContextMenu(contextMenu);
    }


    public void viewDetailInforVehicle() throws IOException {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;
        vehicleHistoryInfoController.selected = selected;
        Stage newStage = new Stage();
        newStage.setTitle("History of vehicle with plate number: " + selected.getPlateNumber());
        Parent root = FXMLLoader.load(getClass().getResource("/vehicleHistoryInfo.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.show();
    }

    public void viewMaintenanceHistoryVehicle() throws IOException {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected==null) return;
        VehicleMaintenanceController.selected = selected;
        Stage newStage = new Stage();
        newStage.setTitle("Maintenance History Of Vehicle With Plate Number: " + selected.getPlateNumber());
        Parent root = FXMLLoader.load(getClass().getResource("/vehicleMaintenance.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.show();
    }


    public void resetField() {
        typeVehicleComboBox.getSelectionModel().select(null);
        lengthText.setText("");
        wideText.setText("");
        highText.setText("");
        plateNumberText.setText("");
        weightText.setText("");
        vehicleTable.getSelectionModel().select(null);
        //typeFuelVehicleComboBox.getSelectionModel().select(null);
        busPriceText.setText("");
        busNumSeatText.setText("");
    }


    public void MouseClicktoUnselectVehicle() {
        vehicleTable.getSelectionModel().select(null);
    }

    public void typeBusChange() {
        if (typeVehicleComboBox.getSelectionModel().getSelectedItem() == TypeVehicle.bus) {
            BusVehicleField.setVisible(true);
        }
        else BusVehicleField.setVisible(false);
    }

////////////////////////////////////////////////////////

    public void showDriverList() throws Exception {
        driverList.clear();

        driverList = connection.getDriver();

        driverIDCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("id"));
        nameDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("name"));
        statusDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, DriverStatus>("status"));
        phoneDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("phoneNumber"));
        addressDiverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("address"));
        plateNumberDriverCol.setCellValueFactory(new PropertyValueFactory<Driver, String>("recentPlateNumber"));
        licenseDriverCol.setCellValueFactory( data->
                new SimpleStringProperty(data.getValue().getLicense().getType().toString()));
        expireDateCol.setCellValueFactory(data->
                new SimpleStringProperty(data.getValue().getLicense().getExpiryDate()));

        TableListDriver.setItems(driverList);

        this.researchDriver();
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

        newDriver.getLicense().setType(licenseDriverComboBox.getValue());

        LocalDate localIssueDate = issueDatePicker.getValue();
        LocalDate localExpiryDate = localIssueDate.plusYears(5);
        String pattern = "dd-MM-yyyy";
        String dateIssue = localIssueDate.format(DateTimeFormatter.ofPattern(pattern));
        String dateExpiry = localExpiryDate.format(DateTimeFormatter.ofPattern(pattern));
        newDriver.getLicense().setIssueDate(dateIssue);
        newDriver.getLicense().setExpiryDate(dateExpiry);

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
        if (seleted.getStatus() == DriverStatus.ON_DUTY) {
            BlankFieldAlert("Tài xế đang trong chuyến đi, vui lòng không xóa");
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
                if(!Objects.equals(seleted.getId(), driverIDText.getText()) )
                {
                    BlankFieldAlert("Can't update ID of Driver");
                    resetFieldDriver();
                    return;
                }
                if(!Objects.equals(seleted.getPhoneNumber(), phoneDriverText.getText()))
                {
                    BlankFieldAlert("Can't update phoneNumber of Driver");
                    resetFieldDriver();
                    return;
                }
                seleted.setId(driverIDText.getText());
                seleted.setName(nameDriverText.getText());
                seleted.setPhoneNumber(phoneDriverText.getText());
                seleted.setAddress(addressDriverText.getText());

                LocalDate localIssueDate = issueDatePicker.getValue();
                LocalDate localExpiryDate = localIssueDate.plusYears(5);
                String pattern = "dd-MM-yyyy";
                String dateIssue = localIssueDate.format(DateTimeFormatter.ofPattern(pattern));
                String dateExpiry = localExpiryDate.format(DateTimeFormatter.ofPattern(pattern));
                seleted.getLicense().setIssueDate(dateIssue);
                seleted.getLicense().setExpiryDate(dateExpiry);


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

    public void researchDriver() {
        FilteredList<Driver> filteredDataDriver = new FilteredList<>(driverList, b -> true);
        researchDriverText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataDriver.setPredicate(vehicle -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (vehicle.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else // Does not match.
                    if (vehicle.getName().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    else return vehicle.getPhoneNumber().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Driver> sortedData = new SortedList<>(filteredDataDriver);
        sortedData.comparatorProperty().bind(TableListDriver.comparatorProperty());
        TableListDriver.setItems(sortedData);
    }


    public void resetFieldDriver() {
        driverIDText.setText(null);
        nameDriverText.setText(null);
        phoneDriverText.setText(null);
        addressDriverText.setText(null);
        issueDatePicker.setValue(null);
        TableListDriver.getSelectionModel().select(null);
    }


    public void mouseRightClicktoHistoryInfoDriver() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("Xem lịch sử chuyến đi");
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    viewHistoryInfoDriver();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        contextMenu.getItems().add(menuItem1);
        TableListDriver.setContextMenu(contextMenu);
    }


    public void viewHistoryInfoDriver() throws IOException {
        Driver selected = TableListDriver.getSelectionModel().getSelectedItem();
        DriverHistoryInfoController.selected = selected;
        Stage newStage = new Stage();
        newStage.setTitle("History of Driver with ID: " + selected.getId());
        Parent root = FXMLLoader.load(getClass().getResource("/driverHistoryInfo.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setResizable(false);
        newStage.show();
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

        LocalDate localDate = LocalDate.parse(selected.getLicense().getIssueDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        issueDatePicker.setValue(localDate);
    }


    public Boolean warningBlankFieldDriver() {
        return driverIDText.getText().isEmpty()
                || nameDriverText.getText().isEmpty()
                || phoneDriverText.getText().isEmpty()
                || addressDriverText.getText().isEmpty()
                || licenseDriverComboBox.getValue() == null
                || issueDatePicker.getValue() == null;
    }

    public static void BlankFieldAlert(String errorStr) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(errorStr);
        alert.showAndWait();
    }

    ////////////////////////////////////////////////////////

    public void showTrip() throws Exception {
        tripList.clear();
        tripList = connection.getOnDutyTrip();
        beginDateTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("begin_date"));
        endDateTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("end_date"));
        beginTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("beginLocation"));
        endTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("endLocation"));
        driverIDTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("driverID"));
        plateNumberTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("plateNumber"));
        costTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("cost"));
        revenueTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("Revenue"));
        TripTable.setItems(tripList);
    }

    public void typeVehicleChange() throws Exception {
        Vehicle v = plateNumberTripComboBox.getSelectionModel().getSelectedItem();
        if (v == null) return;
        if (v.getType() == TypeVehicle.truck) {
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
    }

    public void setTruck(Truck truck) {
        truck.setGoodsType(truckGoodTypeText.getText());
        truck.setGoodsWeight(Double.parseDouble(truckGoodWeightText.getText()));
    }

    public void setContainer(Container container) {
        container.setGoodsType(containerGoodTypeText.getText());
        container.setGoodsWeight(Double.parseDouble(containerGoodWeightText.getText()));
    }

    public boolean warningBlankFieldTrip() {
        return beginTripText.getText().isEmpty()
                || endTripText.getText().isEmpty()
                || typeVehicleTripComboBox.getValue() == null
                || driverIDTripComboBox.getValue() == null
                || plateNumberTripComboBox.getValue() == null
                || beginTripDatePicker.getValue() == null;
        //doanh thu
    }

    public void updateInforTrip() throws Exception {
        if (warningBlankFieldTrip()) {
            BlankFieldAlert("Please fill all blank fields");
            return;
        }
        Vehicle v = plateNumberTripComboBox.getSelectionModel().getSelectedItem();
        if (v == null) {
            BlankFieldAlert("Chưa chọn xe");
            return;
        }
        double cost = v.getFuel_v().getPricePerLiter() * v.getFuel_per_kilometer() * routeMatrix.getDistance() / 1000;
        cost = Math.round(cost / 1000) * 1000;


        switch (v) {
            case Car ignored -> {
                if (carCustomerIDText.getText().isEmpty()
                        || carCustomerPhonenumText.getText().isEmpty()
                        || carCustomerAddressText.getText().isEmpty()
                        || carCustomerNameText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
                double rvnCar = cost * 1.2;
                revenueText.setText(String.valueOf(rvnCar));
            }
            case Truck ignored -> {
                if (truckGoodTypeText.getText().isEmpty()
                        || truckGoodWeightText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
                double rvnTruck = cost * 1.2 + Double.parseDouble(truckGoodWeightText.getText())*5000;
                revenueText.setText(String.valueOf(rvnTruck));
            }
            case Container ignored -> {
                if (containerGoodTypeText.getText().isEmpty()
                        || containerGoodWeightText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
                double rvnContainer = cost * 1.2 + Double.parseDouble(containerGoodWeightText.getText())*5000;
                revenueText.setText(String.valueOf(rvnContainer));
            }
            case Bus selectedBus -> {
                revenueText.setEditable(false);
                if (busNumCustomerText.getText().isEmpty()) {
                    BlankFieldAlert("Please fill all blank fields");
                    return;
                }
                double rvnBus = selectedBus.getPricePerSeat() * Integer.parseInt(busNumCustomerText.getText());
                revenueText.setText(String.valueOf(rvnBus));
            }
            default -> BlankFieldAlert("Sau khi nhập biển số, nhấn Enter để cập nhật loại xe");
        }
        costText.setText(String.valueOf(cost));
        distanceCoverTripText.setText(String.valueOf(routeMatrix.getDistance()/1000));
        LocalDateTime begin_dt = beginTripDatePicker.getDateTimeValue();
        LocalDateTime end_dt = begin_dt.plus(Duration.ofMillis(routeMatrix.getDuration()));
        endTripDatePicker.setText(dateTimetoString(end_dt));

        durationNow = String.valueOf(routeMatrix.getDuration());
    }

    public void addTrip() throws Exception {
        if (endTripDatePicker.getText().isEmpty() || costText.getText().isEmpty()) {
            BlankFieldAlert("Hãy tính toán chuyến đi trước");
            return;
        }

        Vehicle v = plateNumberTripComboBox.getSelectionModel().getSelectedItem();
        Driver d = driverIDTripComboBox.getSelectionModel().getSelectedItem();

        Trip newTrip = new Trip();

        UUID uuid = UUID.randomUUID();
        newTrip.setTripID(durationNow + " " + uuid.toString());
        durationNow = "";

        newTrip.setStatus(TripStatus.ON_DUTY);
        newTrip.setDriverID(d.getId());
        newTrip.setPlateNumber(v.getPlateNumber());
        newTrip.setBegin(new Coordinate(selectedHitBegin.getPoint().getLng(), selectedHitBegin.getPoint().getLat()));
        newTrip.setEnd(new Coordinate(selectedHitEnd.getPoint().getLng(), selectedHitEnd.getPoint().getLat()));
        newTrip.setBeginLocation(MapRequest.getInstance().getAddressFromCoordinate(newTrip.getBegin()));
        newTrip.setEndLocation(MapRequest.getInstance().getAddressFromCoordinate(newTrip.getEnd()));
        newTrip.setBegin_date(dateTimetoString(beginTripDatePicker.getDateTimeValue()));
        newTrip.setEnd_date(endTripDatePicker.getText());
        newTrip.setDistanceCover(Double.parseDouble(distanceCoverTripText.getText()));
        newTrip.setCost(Double.parseDouble(costText.getText()));
        newTrip.setRevenue(Double.parseDouble(revenueText.getText()));
        //đã xóa cập nhật khi xe và tài xế chưa đi
        v.setDriverID(d.getId());
        v.setStatus(VehicleStatus.ON_DUTY);
        v.addTrip(newTrip.getTripID());
        switch (v) {
            case Car selectedCar -> {
                setCar(selectedCar);
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
                try {
                    FireBase fireBase = FireBase.getInstance();
                    fireBase.editVehicle(v.getPlateNumber(), selectedContainer);
                    showVehicleList();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case Bus selectedBus -> {
                selectedBus.setNumberOfCustomer(Integer.parseInt(busNumCustomerText.getText()));
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
        d.addTrip(newTrip.getTripID());
        d.setStatus(DriverStatus.ON_DUTY);
        d.setRecentPlateNumber(v.getPlateNumber());
        try {
            FireBase fireBase = FireBase.getInstance();
            fireBase.editDriver(d);
            showDriverList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        FireBase.getInstance().addTrip(newTrip);
        showTrip();
        resetFieldTrip();
    }


    public void refreshTrip() throws Exception {
        // đã cập nhật trip khi chuyến đi xong
        FireBase fireBase = FireBase.getInstance();
        for (Trip trip : tripList) {
            LocalDateTime dateTime = stringtoDateTime(trip.getEnd_date());
            if (dateTime.isBefore(LocalDateTime.now())) {
                Vehicle v = fireBase.getVehicle(trip.getPlateNumber());
                if (v == null) {
                    BlankFieldAlert("null vehicle????????");
                    return;
                }
                v.setDriverID("");
                v.setDistanceCoverFromLastRepair(v.getDistanceCoverFromLastRepair() + trip.getDistanceCover());
                if(v.getDistanceCoverFromLastRepair() > v.getLimitKilometers()) v.setStatus(VehicleStatus.NEED_REPAIR);
                else v.setStatus(VehicleStatus.NONE);
                fireBase.editVehicle(v.getPlateNumber(), v);
                showVehicleList();

                Driver d = fireBase.getDriver(trip.getDriverID());
                if (d == null) {
                    BlankFieldAlert("null driver????????");
                    return;
                }
                d.setRecentPlateNumber("");
                d.setDistanceCoverAll(d.getDistanceCoverAll() + trip.getDistanceCover());
                d.setStatus(DriverStatus.NONE);
                fireBase.editDriver(d);
                showDriverList();

                fireBase.editStatusTrip(trip.getTripID(), TripStatus.NONE);
            }
        }
        showTrip();
        resetFieldTrip();
    }

    public void resetFieldTrip() {
        beginTripText.setText("");
        endTripText.setText("");
        beginTripDatePicker.setValue(null);
        endTripDatePicker.setText("");
        typeVehicleTripComboBox.getSelectionModel().select(null);
        driverIDTripComboBox.getSelectionModel().select(null);
        plateNumberTripComboBox.getSelectionModel().select(null);
        revenueText.setText("");
        costText.setText("");
        distanceCoverTripText.setText("");

        truckGoodTypeText.setText("");
        truckGoodWeightText.setText("");
        carCustomerNameText.setText("");
        carCustomerAddressText.setText("");
        carCustomerIDText.setText("");
        carCustomerPhonenumText.setText("");
        containerGoodTypeText.setText("");
        containerGoodWeightText.setText("");
        busNumSeatText.setText("");
    }
    public void mouseRightClickTriptoMap(){
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
        TripTable.setContextMenu(contextMenuTrip);
    }
    public void warningBlankBeginEnd() {
        if (beginTripText.getText().isEmpty() || endTripText.getText().isEmpty()) {
            BlankFieldAlert("Vui lòng chọn điểm đi và đến trước khi chọn loại xe");
        }
    }
    public void warningBlankTypeVehicle() {
        if (typeVehicleTripComboBox.getValue() == null) {
            BlankFieldAlert("Vui lòng chọn loại xe để chúng tôi có thể đề xuất");
        }
    }
    public void formatComboBox() {
        plateNumberTripComboBox.setCellFactory(lv -> new ListCell<Vehicle>() {
            @Override
            protected void updateItem(Vehicle item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getType().toString() + " - " + item.getPlateNumber());
                }
            }
        });
        // Custom cell for displaying the selected item
        plateNumberTripComboBox.setButtonCell(new ListCell<Vehicle>() {
            @Override
            protected void updateItem(Vehicle item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getType().toString() + " - " + item.getPlateNumber());
                }
            }
        });
        driverIDTripComboBox.setCellFactory(lv -> new ListCell<Driver>() {
            @Override
            protected void updateItem(Driver item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getId());
                }
            }
        });
        // Custom cell for displaying the selected item
        driverIDTripComboBox.setButtonCell(new ListCell<Driver>() {
            @Override
            protected void updateItem(Driver item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getId());
                }
            }
        });
    }
    public void initDriverandVehicleComboBox() throws Exception {
        List begin = selectedHitBegin.getPoint().getList();
        List end = selectedHitEnd.getPoint().getList();

        routeMatrix = mapRequest.getDistanceMatrix(begin, end);

        formatComboBox();
        ObservableList<Driver> driverObservableList = AvailableDriver.getDriver(routeMatrix.getDistance()/1000);
        driverIDTripComboBox.setItems(driverObservableList);
        ObservableList<Vehicle> vehicleObservableList = AvailableVehicle.getVehicle(typeVehicleTripComboBox.getSelectionModel().getSelectedItem());
        plateNumberTripComboBox.setItems(vehicleObservableList);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("");
        alert.setContentText("Đã đề xuất tài xế và xe, vui lòng chọn tài xế, xe và các trường còn lại của xe và ấn tính toán để hoàn tất");
        alert.showAndWait();
    }



    ///////////////////////////////////////////////////////
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
    public void switchForm(ActionEvent e){
        if (e.getSource()==HomeButton){
            showHome();
            HomePane.setVisible(true);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(false);
            TripPane.setVisible(false);
        }
        else if (e.getSource()==VehicleButton) {
            try {
                showVehicleList();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            HomePane.setVisible(false);
            VehiclePane.setVisible(true);
            DriverPane.setVisible(false);
            TripPane.setVisible(false);
        }
        else if(e.getSource()==DriverButton) {
            try {
                showDriverList();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            HomePane.setVisible(false);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(true);
            TripPane.setVisible(false);
        }
        else if (e.getSource()==LogOutButton){
            logOut();
        }
        else if(e.getSource()==TripButton){
            try {
                showTrip();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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



}