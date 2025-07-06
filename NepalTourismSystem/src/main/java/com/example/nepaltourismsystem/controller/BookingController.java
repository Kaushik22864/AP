package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.*;
import com.example.nepaltourismsystem.InvalidDateException;
import com.example.nepaltourismsystem.NullObjectException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class BookingController {

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> touristColumn;
    @FXML private TableColumn<Booking, String> guideColumn;
    @FXML private TableColumn<Booking, String> attractionColumn;
    @FXML private TableColumn<Booking, String> dateColumn;

    @FXML private TextField touristField;
    @FXML private TextField guideField;
    @FXML private TextField attractionField;
    @FXML private DatePicker datePicker;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;

    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        touristColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTourist().getName()));
        guideColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGuide().getName()));
        attractionColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAttraction().getName()));
        dateColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));

        bookingTable.setItems(bookingList);

        bookingTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> populateFields(newSelection));

        addButton.setOnAction(e -> addBooking());
        updateButton.setOnAction(e -> updateBooking());
        deleteButton.setOnAction(e -> deleteBooking());
        clearButton.setOnAction(e -> clearFields());
    }

    private void addBooking() {
        try {
            Booking booking = new Booking(
                    dummyTourist(touristField.getText()),
                    dummyGuide(guideField.getText()),
                    dummyAttraction(attractionField.getText()),
                    datePicker.getValue(),
                    "Pending"
            );
            bookingList.add(booking);
            clearFields();
        } catch (NullObjectException | InvalidDateException e) {
            showAlert("Invalid Input", e.getMessage());
        } catch (EmptyFieldException e) {
            throw new RuntimeException(e);
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setTourist(dummyTourist(touristField.getText()));
                selected.setGuide(dummyGuide(guideField.getText()));
                selected.setAttraction(dummyAttraction(attractionField.getText()));
                selected.setDate(datePicker.getValue());
                selected.setStatus("Pending");

                bookingTable.refresh();
                clearFields();
            } catch (NullObjectException | InvalidDateException e) {
                showAlert("Invalid Input", e.getMessage());
            } catch (EmptyFieldException e) {
                throw new RuntimeException(e);
            } catch (NegativeValueException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("No Selection", "Please select a booking to update.");
        }
    }

    private void deleteBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookingList.remove(selected);
            clearFields();
        } else {
            showAlert("No Selection", "Please select a booking to delete.");
        }
    }

    private void clearFields() {
        touristField.clear();
        guideField.clear();
        attractionField.clear();
        datePicker.setValue(null);
        bookingTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Booking booking) {
        if (booking != null) {
            touristField.setText(booking.getTourist().getName());
            guideField.setText(booking.getGuide().getName());
            attractionField.setText(booking.getAttraction().getName());
            datePicker.setValue(booking.getDate());
        }
    }

    // Temporary dummy object creation methods
    private Tourist dummyTourist(String name) throws NullObjectException, EmptyFieldException {
        if (name == null || name.isBlank()) throw new NullObjectException("Tourist");
        return new Tourist(name, "Nepal", "N/A", "N/A");
    }

    private Guide dummyGuide(String name) throws NullObjectException, NegativeValueException, EmptyFieldException {
        if (name == null || name.isBlank()) throw new NullObjectException("Guide");
        return new Guide(name, java.util.Collections.singletonList("English"), 1, "N/A");
    }

    private Attraction dummyAttraction(String name) throws NullObjectException, EmptyFieldException {
        if (name == null || name.isBlank()) throw new NullObjectException("Attraction");
        return new Attraction(name, "Cultural", "Kathmandu", "Description");
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
