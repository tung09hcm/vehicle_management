package com.management.vehicle.gui;

import com.management.vehicle.vehicle.Vehicle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class vehicleHistoryInfoController implements Initializable {

    @FXML
    private TextField addVehicleHistoryText;

    @FXML
    private ListView<String> historyVehicleListView;

    ObservableList<String> historyVehicleOList = FXCollections.observableArrayList();

    @FXML
    private TextField searchHistoryText;

    static Vehicle selected = new Vehicle();

    private final List<String> historyList = selected.getHistory();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        historyVehicleOList.addAll(selected.getHistory());
        historyVehicleListView.setItems(historyVehicleOList);
        historyVehicleListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                addVehicleHistoryText.setText(historyVehicleListView.getSelectionModel().getSelectedItem());
            }
        });
        FilteredList<String> filteredDataHistory = new FilteredList<>(historyVehicleOList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchHistoryText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataHistory.setPredicate(h -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (h.contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<String> sortedData = new SortedList<>(filteredDataHistory);
        historyVehicleListView.setItems(sortedData);
    }
    @FXML
    void addVehicleHistory(ActionEvent event) {
        if (addVehicleHistoryText.getText().isEmpty()) dashboardController.BlankFieldAlert("Please fill all blank fields");
        else {
            historyVehicleOList.add(addVehicleHistoryText.getText());
            historyList.add(addVehicleHistoryText.getText());
            selected.setHistory(historyList);
            addVehicleHistoryText.setText("");
        }
    }
    @FXML
    void updateVehicleHistory(ActionEvent event) {
        int index = historyVehicleListView.getSelectionModel().getSelectedIndex();
        historyList.set(index, addVehicleHistoryText.getText());
        selected.setHistory(historyList);
        historyVehicleOList.set(index, addVehicleHistoryText.getText());
    }
}