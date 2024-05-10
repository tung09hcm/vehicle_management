package com.management.vehicle.gui;

import com.management.vehicle.request.FireBase;
import com.management.vehicle.vehicle.Vehicle;
import com.management.vehicle.vehicle.VehicleStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class VehicleMaintenanceController implements Initializable {
    @FXML
    private AnchorPane TripPane;

    @FXML
    private TextField beginDayText;

    @FXML
    private TextField endDayText;

    @FXML
    private Label licenseVehicleLabel;

    @FXML
    private Label plateNumberVehicleLabel;

    @FXML
    private Label statusVehicleLabel;

    @FXML
    private Label typeVehicleLabel;

    @FXML
    private Label distanceVehicleLabel;


    @FXML
    private TableColumn<String, String> beginDayMaintenanceColumn;

    @FXML
    private TableColumn<String, String> endDayMaintenanceColumn;

    @FXML
    private TableView<String> vehicleMaintenanceTable;

    ObservableList<String> listMaintenance = FXCollections.observableArrayList();


    public static Vehicle selected;

    @FXML
    void maintenanceVehicle(ActionEvent event) {
        if(selected==null) return;
        if(selected.getStatus() == VehicleStatus.ON_LEAVE) {
            BlankFieldAlert("Xe đang được bảo dưỡng!");
        }
        else if(selected.getStatus() == VehicleStatus.ON_DUTY) {
            BlankFieldAlert("Xe đang di chuyển! Không thể bảo dưỡng!");
        }
        else  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Maintenance vehicle");

            if(selected.getStatus() == VehicleStatus.NONE) {
                alert.setContentText("Xe chưa đến ngưỡng bảo dưỡng, Bạn vẫn muốn bảo dưỡng ?");
            }
            else if(selected.getStatus() == VehicleStatus.NEED_REPAIR) {
                alert.setContentText("Bạn muốn bảo dưỡng xe ?");
            }

            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(respone -> {
                if (respone == ButtonType.YES) {
                    selected.setStatus(VehicleStatus.ON_LEAVE);
                    selected.setDistanceCoverFromLastRepair(0);
                    LocalDate localDate = LocalDate.now();
                    String beginDayMaintenance = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    selected.setLast_repair_date(beginDayMaintenance);

                    try {
                        FireBase.getInstance().editVehicle(selected.getPlateNumber(), selected);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    displayMaintenanceDayVehicle();
                    statusVehicleLabel.setText(selected.getStatus().toString());
                }
            });
        }
    }

    void displayHistoryMaintenanceVehicle() {
        if(selected==null) return;

        listMaintenance.clear();
        listMaintenance = FXCollections.observableArrayList(selected.getMaintenanceHistory());
        for (String str: listMaintenance) {
            System.out.println(str);
        }
        beginDayMaintenanceColumn.setCellValueFactory(data->{
            return new SimpleStringProperty(data.getValue());
        });
        endDayMaintenanceColumn.setCellValueFactory(data->{
            LocalDate localDate = LocalDate.parse(data.getValue(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            localDate = localDate.plusDays(2);
            String endDayMaintenance = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return new SimpleStringProperty(endDayMaintenance);
        });

        vehicleMaintenanceTable.setItems(listMaintenance);
    }

    void displayMaintenanceDayVehicle() {
        if(selected==null) return;

        if(selected.getStatus()!=VehicleStatus.ON_LEAVE) {
            beginDayText.setText(null);
            endDayText.setText(null);
        }

        else {
            beginDayText.setText(selected.getLast_repair_date());

            LocalDate localDate = LocalDate.parse(selected.getLast_repair_date(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            localDate = localDate.plusDays(2);
            String endDayMaintenance = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            endDayText.setText(endDayMaintenance);
        }
    }


    public void displayInforVehilce() {
        if(selected==null) return;

        licenseVehicleLabel.setText(selected.getLicense().toString());
        plateNumberVehicleLabel.setText(selected.getPlateNumber());
        statusVehicleLabel.setText(selected.getStatus().toString());
        typeVehicleLabel.setText(selected.getType().toString());

        // Create a DecimalFormat object with the desired format
        DecimalFormat decimalFormat = new DecimalFormat("#.000");

        // Apply the formatting to the number
        String roundedNumber = decimalFormat.format(selected.getDistanceCoverFromLastRepair());
        distanceVehicleLabel.setText(roundedNumber);

    }


    public void BlankFieldAlert(String errorStr) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(errorStr);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayHistoryMaintenanceVehicle();
        displayMaintenanceDayVehicle();
        displayInforVehilce();
    }
}
