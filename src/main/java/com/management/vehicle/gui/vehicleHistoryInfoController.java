package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.trip.Trip;
import com.management.vehicle.vehicle.Vehicle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class vehicleHistoryInfoController implements Initializable {

    @FXML
    private AnchorPane TripPane;


    @FXML
    private TableColumn<Trip, String> beginDateTripColumn;

    @FXML
    private TableColumn<Trip, String> beginTripColumn;

    @FXML
    private TableColumn<Trip, String> distanceCoverTripColumn;

    @FXML
    private TableColumn<Trip, String> endDateTripColumn;

    @FXML
    private TableColumn<Trip, String> endTripColumn;

    @FXML
    private TableView<Trip> historyTripVehicleTable;

    @FXML
    private TableColumn<Trip, String> tripCostColumn;

    @FXML
    private TableColumn<Trip, String> tripRevenueColumn;


    @FXML
    private Label distanceCoverVehicleLabel;

    @FXML
    private Label hightVehicleLabel;

    @FXML
    private Label lengthVehicleLabel;

    @FXML
    private Label licenseVehicleLabel;

    @FXML
    private Label plateNumberVehicleLabel;

    @FXML
    private Label statusVehicleLabel;

    @FXML
    private Label typeVehicleLabel;

    @FXML
    private Label weightVehcleLable;

    @FXML
    private Label wideVehicleLabel;

    @FXML
    private Label DriverIDLabel;


    public static Vehicle selected;

    private ObservableList<Trip> listTrip = FXCollections.observableArrayList();

    @FXML
    void refreshData(ActionEvent event) {
        setInfoVehicle();
        setHistoryTripVehicle();
    }

    void setInfoVehicle() {
        if(selected==null) return;

        typeVehicleLabel.setText(selected.getType().toString());
        plateNumberVehicleLabel.setText(selected.getPlateNumber());
        statusVehicleLabel.setText(selected.getStatus().toString());
        DriverIDLabel.setText(selected.getDriverID());
        licenseVehicleLabel.setText(selected.getLicense().toString());
        weightVehcleLable.setText(String.valueOf(selected.getWeight()));
        lengthVehicleLabel.setText(String.valueOf(selected.getLength()));
        wideVehicleLabel.setText(String.valueOf(selected.getWide()));
        hightVehicleLabel.setText(String.valueOf(selected.getHigh()));
        distanceCoverVehicleLabel.setText(String.valueOf(selected.getDistanceCover()));
    }

    void setHistoryTripVehicle(){
        if(selected==null) return;

        listTrip.clear();

        for(String tripID : selected.getHistory())
        {
            System.out.println(tripID);
            Trip trip = null;
            try {
                trip = FireBase.getInstance().getTrip(tripID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            listTrip.add(trip);
        }

        beginDateTripColumn.setCellValueFactory     (new PropertyValueFactory<>("begin_date"));
        endDateTripColumn.setCellValueFactory       (new PropertyValueFactory<>("end_date"));
        tripCostColumn.setCellValueFactory          (new PropertyValueFactory<>("Cost"));
        tripRevenueColumn.setCellValueFactory       (new PropertyValueFactory<>("Revenue"));
        distanceCoverTripColumn.setCellValueFactory (new PropertyValueFactory<>("distanceCover"));
        beginTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("beginLocation"));
        endTripColumn.setCellValueFactory(new PropertyValueFactory<Trip, String>("endLocation"));

        historyTripVehicleTable.setItems(listTrip);

    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setInfoVehicle();
        setHistoryTripVehicle();
    }
}