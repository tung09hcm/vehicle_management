package com.management.vehicle.gui;

import com.management.vehicle.driver.Driver;
import com.management.vehicle.request.FireBase;
import com.management.vehicle.request.MapRequest;
import com.management.vehicle.trip.Trip;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class DriverHistoryInfoController implements Initializable {

    @FXML
    private AnchorPane TripPane;

    @FXML
    private Label addressDriverLabel;

    @FXML
    private Label driverIDLabel;

    @FXML
    private Label issueDateDriverLabel;

    @FXML
    private Label licenseDriverLabel;

    @FXML
    private Label nameDriverLabel;

    @FXML
    private Label phoneDriverLabel;

    @FXML
    private Label statusDriverLabel;


    @FXML
    private TableColumn<Trip, String> beginDateTripColumn;

    @FXML
    private TableColumn<Trip, String> endDateTripColumn;

    @FXML
    private TableColumn<Trip, String> beginTripColumn;

    @FXML
    private TableColumn<Trip, String> endTripColumn;

    @FXML
    private TableView<Trip> historyTripDriverTable;

    @FXML
    private TableColumn<Trip, String> plateNumberTripColumn;

    @FXML
    private TableColumn<Trip, String> tripRevenueColumn;

    @FXML
    private TableColumn<Trip, String> tripCostColumn;


    ObservableList<Trip> listTrip = FXCollections.observableArrayList();

    public static Driver selected;



    public void setInfoDriver() {
        if (selected==null) return;

        driverIDLabel.setText(selected.getId());
        nameDriverLabel.setText(selected.getName());
        phoneDriverLabel.setText(selected.getPhoneNumber());
        addressDriverLabel.setText(selected.getAddress());
        licenseDriverLabel.setText(selected.getLicense().getType().toString());
        issueDateDriverLabel.setText(selected.getLicense().getIssueDate());
        statusDriverLabel.setText(selected.getStatus().toString());
    }

    public void setHistoryTripDriver() {
        if (selected==null) return;

        listTrip.clear();

        for(String tripID : selected.getHistory()) {
            Trip trip = null;
            try {
                trip = FireBase.getInstance().getTrip(tripID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            listTrip.add(trip);
        }

        System.out.println("size of tripList: "+ listTrip.size());

        beginDateTripColumn.setCellValueFactory     (new PropertyValueFactory<>("begin_date"));
        endDateTripColumn.setCellValueFactory       (new PropertyValueFactory<>("end_date"));
        plateNumberTripColumn.setCellValueFactory   (new PropertyValueFactory<>("plateNumber"));
        tripCostColumn.setCellValueFactory          (new PropertyValueFactory<>("Cost"));
        tripRevenueColumn.setCellValueFactory       (new PropertyValueFactory<>("Revenue"));
        beginTripColumn.setCellValueFactory(data->{
            Trip trip = data.getValue();
            String address = "";
            try {
                address = MapRequest.getInstance().getAddressFromCoordinate(trip.getBegin());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return new SimpleStringProperty(address);
        });
        endTripColumn.setCellValueFactory(data -> {
            Trip trip = data.getValue();
            String address = "";
            try {
                address = MapRequest.getInstance().getAddressFromCoordinate(trip.getEnd());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return new SimpleStringProperty(address);
        });

        historyTripDriverTable.setItems(listTrip);
    }

    public void refreshData(ActionEvent e) {
        setInfoDriver();
        setHistoryTripDriver();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInfoDriver();
        setHistoryTripDriver();
    }

}
