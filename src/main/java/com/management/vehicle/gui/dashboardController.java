package com.management.vehicle.gui;

import com.jfoenix.controls.JFXButton;
import com.management.vehicle.driver.Driver;
import com.management.vehicle.driver.DriverStatus;
import com.management.vehicle.license.LicenseLevel;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.vehicle.*;
import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private TableColumn<Vehicle, Integer> distanceCoverColumn;

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
    private TextField carRentalDateText;

    @FXML
    private TextField carReturnDateText;

    @FXML
    private TextField containerGoodTypeText;

    @FXML
    private TextField containerGoodWeightText;

/////////////////////////////////////////////////////////////////////////////////


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
    ObservableList<Trip> listTrip = FXCollections.observableArrayList();

/////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeVehicleComboBox.setItems(typeVehicleObservableList);
        licenseLevelComboBox.setItems(licenseLevelObservableList);
        vehicleStatusComboBox.setItems(vehicleStatusObservableList);
        statusDriverComboBox.setItems(statusDriverObservableList);
        licenseDriverComboBox.setItems(licenseLevelObservableList);

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

        showHome();
        HomePane.setVisible(true);
        VehiclePane.setVisible(false);
        DriverPane.setVisible(false);

    }

    public void showVehicleList() throws Exception {
        vehicleList = connection.getVehicle();

        driverofVehicleColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("driverID"));
        distanceCoverColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("distanceCover"));
        typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, TypeVehicle>("type"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("length"));
        wideColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("wide"));
        highColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("high"));
        plateNumberColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("plateNumber"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("weight"));
        vehicleStatusColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, VehicleStatus>("status"));
        licenseLevelColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, LicenseLevel>("license"));

        vehicleTable.setItems(vehicleList);

        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
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
        contextMenu.getItems().add(menuItem1);
        vehicleTable.setContextMenu(contextMenu);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Vehicle> filteredDataVehicle = new FilteredList<>(vehicleList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchVehicleText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataVehicle.setPredicate(vehicle -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (vehicle.getPlateNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (vehicle.getType().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (vehicle.getDriverID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Vehicle> sortedData = new SortedList<>(filteredDataVehicle);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(vehicleTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        vehicleTable.setItems(sortedData);
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
        switch (selected) {
            case Car selectedCar -> {
                carCustomerNameText.setText(selectedCar.getCustomername());
                carCustomerAddressText.setText(selectedCar.getCustomeraddress());
                carCustomerIDText.setText(selectedCar.getCustomerid());
                carCustomerPhonenumText.setText(selectedCar.getCustomerphoneNumber());
                carRentalDateText.setText(selectedCar.getRentalDate());
                carReturnDateText.setText(selectedCar.getReturnDate());
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
            default -> {
            }
        }
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
    public static void BlankFieldVehicleAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    }

    public void SetFieldtoVehicle(Vehicle newVehicle) {
        newVehicle.setDistanceCover(Integer.parseInt(distanceCoverText.getText()));
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
    public void typeVehicleChange() {
        if (typeVehicleComboBox.getValue() == TypeVehicle.truck) {
            StandardField.setVisible(false);
            TruckField.setVisible(true);
            ContainerField.setVisible(false);
            CarField.setVisible(false);
            BusField.setVisible(false);
        }
        else if (typeVehicleComboBox.getValue() == TypeVehicle.container) {
            StandardField.setVisible(false);
            TruckField.setVisible(false);
            ContainerField.setVisible(true);
            CarField.setVisible(false);
            BusField.setVisible(false);
        }
        else if (typeVehicleComboBox.getValue() == TypeVehicle.car) {
            StandardField.setVisible(false);
            TruckField.setVisible(false);
            ContainerField.setVisible(false);
            CarField.setVisible(true);
            BusField.setVisible(false);
        }
        else if (typeVehicleComboBox.getValue() == TypeVehicle.bus) {
            StandardField.setVisible(false);
            TruckField.setVisible(true);
            ContainerField.setVisible(false);
            CarField.setVisible(false);
            BusField.setVisible(true);
        }
        else {
            StandardField.setVisible(true);
            TruckField.setVisible(false);
            ContainerField.setVisible(false);
            CarField.setVisible(false);
            BusField.setVisible(false);
        }
    }
    public void setCar(Car car) {
        car.setCustomername(carCustomerNameText.getText());
        car.setCustomerid(carCustomerIDText.getText());
        car.setCustomeraddress(carCustomerAddressText.getText());
        car.setCustomerphoneNumber(carCustomerPhonenumText.getText());
        car.setRentalDate(carRentalDateText.getText());
        car.setReturnDate(carReturnDateText.getText());
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
        if (warningBlankFieldVehicle()) BlankFieldVehicleAlert();
        switch (typeVehicleComboBox.getValue()) {
            case TypeVehicle.car -> {
                if (warningBlankFieldVehicle()
                        || carCustomerNameText.getText().isEmpty()
                        || carCustomerAddressText.getText().isEmpty()
                        || carCustomerPhonenumText.getText().isEmpty()
                        || carCustomerIDText.getText().isEmpty()
                        || carReturnDateText.getText().isEmpty()
                        || carRentalDateText.getText().isEmpty()
                ) BlankFieldVehicleAlert();
                else {
                    Car newCar = new Car();
                    SetFieldtoVehicle(newCar);
                    setCar(newCar);

                    FireBase fireBase = FireBase.getInstance();
                    fireBase.addVehicle(newCar);
                    showVehicleList();
                    resetField();

                }
            }
            case TypeVehicle.truck -> {
                if (warningBlankFieldVehicle()
                        || truckGoodWeightText.getText().isEmpty()
                        || truckGoodTypeText.getText().isEmpty()
                ) BlankFieldVehicleAlert();
                else {
                    Truck newTruck = new Truck();
                    SetFieldtoVehicle(newTruck);
                    setTruck(newTruck);

                    FireBase fireBase = FireBase.getInstance();
                    fireBase.addVehicle(newTruck);
                    showVehicleList();
                    resetField();
                }
            }
            case TypeVehicle.container -> {
                if (warningBlankFieldVehicle()
                        || containerGoodWeightText.getText().isEmpty()
                        || containerGoodTypeText.getText().isEmpty()
                ) BlankFieldVehicleAlert();
                else {
                    Container newContainer = new Container();
                    SetFieldtoVehicle(newContainer);
                    setContainer(newContainer);


                    FireBase fireBase = FireBase.getInstance();
                    fireBase.addVehicle(newContainer);
                    showVehicleList();
                    resetField();
                }
            }
            case TypeVehicle.bus -> {
                if (warningBlankFieldVehicle()
                        || busNumSeatText.getText().isEmpty()
                        || busPriceText.getText().isEmpty()
                        || busNumCustomerText.getText().isEmpty()
                ) BlankFieldVehicleAlert();
                else {
                    Bus newBus = new Bus();
                    SetFieldtoVehicle(newBus);
                    setBus(newBus);

                    FireBase fireBase = FireBase.getInstance();
                    fireBase.addVehicle(newBus);
                    showVehicleList();
                    resetField();
                }
            }
            default -> {
            }
        }

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
                        if (warningBlankFieldVehicle()
                                || carCustomerNameText.getText().isEmpty()
                                || carCustomerAddressText.getText().isEmpty()
                                || carCustomerPhonenumText.getText().isEmpty()
                                || carCustomerIDText.getText().isEmpty()
                                || carReturnDateText.getText().isEmpty()
                                || carRentalDateText.getText().isEmpty()
                        ) BlankFieldVehicleAlert();
                        SetFieldtoVehicle(selectedCar);
                        setCar(selectedCar);



                    }
                    case Truck selectedTruck -> {
                        if (warningBlankFieldVehicle()
                                || truckGoodWeightText.getText().isEmpty()
                                || truckGoodTypeText.getText().isEmpty()
                        ) BlankFieldVehicleAlert();
                        SetFieldtoVehicle(selectedTruck);
                        setTruck(selectedTruck);


                    }
                    case Container selectedContainer -> {
                        if (warningBlankFieldVehicle()
                                || containerGoodWeightText.getText().isEmpty()
                                || containerGoodTypeText.getText().isEmpty()
                        ) BlankFieldVehicleAlert();
                        SetFieldtoVehicle(selectedContainer);
                        setContainer(selectedContainer);

                    }
                    case Bus selectedBus -> {
                        if (warningBlankFieldVehicle()
                                || busNumSeatText.getText().isEmpty()
                                || busPriceText.getText().isEmpty()
                                || busNumCustomerText.getText().isEmpty()
                        ) BlankFieldVehicleAlert();
                        SetFieldtoVehicle(selectedBus);
                        setBus(selectedBus);

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
        truckGoodTypeText.setText("");
        truckGoodWeightText.setText("");
        carCustomerNameText.setText("");
        carCustomerAddressText.setText("");
        carCustomerIDText.setText("");
        carCustomerPhonenumText.setText("");
        carRentalDateText.setText("");
        carReturnDateText.setText("");
        containerGoodTypeText.setText("");
        containerGoodWeightText.setText("");
        busNumCustomerText.setText("");
        busPriceText.setText("");
        busNumSeatText.setText("");
    }
    public void MouseClicktoUnselectVehicle() {
        vehicleTable.getSelectionModel().select(null);
    }

////////////////////////////////////////////////////////

    public void showDriverList() throws Exception {

        System.out.println("SIGNAL on showDriverList()");

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

        newDriver.getLicense().setType(licenseDriverComboBox.getValue());
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
                    BlankFieldVehicleAlert();
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
                    if(Objects.equals(driver.getPhoneNumber(), phoneDriverText.getText()))
                    {
                        BlankFieldAlert("Same phoneNumber of driver error");
                        resetFieldDriver();
                        return;
                    }
                }
                seleted.setId(driverIDText.getText());
                seleted.setName(nameDriverText.getText());
                seleted.setPhoneNumber(phoneDriverText.getText());
                seleted.setAddress(addressDriverText.getText());
                seleted.setStatus(statusDriverComboBox.getValue());
                seleted.getLicense().setType(licenseDriverComboBox.getValue());

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
        licenseDriverComboBox.getSelectionModel().select(null);
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
        licenseDriverComboBox.getSelectionModel().select(selected.getLicense().getType());
        statusDriverComboBox.getSelectionModel().select(selected.getStatus());

        LocalDate localDate = LocalDate.parse(selected.getLicense().getExpiryDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        issueDatePicker.setValue(localDate);
    }

    public Boolean warningBlankFieldDriver() {
        return driverIDText.getText().isEmpty()
                || nameDriverText.getText().isEmpty()
                || phoneDriverText.getText().isEmpty()
                || addressDriverText.getText().isEmpty()
                || statusDriverComboBox.getSelectionModel().getSelectedItem() == null
                || licenseDriverComboBox.getSelectionModel().getSelectedItem() == null;
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
        homeNumberTripLabel.setText(String.valueOf(listTrip.size()));
    }


    public void switchForm(ActionEvent e){
        if (e.getSource()==HomeButton){
            showHome();
            HomePane.setVisible(true);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(false);
        }
        else if (e.getSource()==VehicleButton) {
            HomePane.setVisible(false);
            VehiclePane.setVisible(true);
            DriverPane.setVisible(false);
        }
        else if(e.getSource()==DriverButton) {
            HomePane.setVisible(false);
            VehiclePane.setVisible(false);
            DriverPane.setVisible(true);
        }
        else if (e.getSource()==LogOutButton){
            logOut();
        }
        else {
            mainMap.display();
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


