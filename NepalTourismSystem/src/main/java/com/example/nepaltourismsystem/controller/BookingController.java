package com.example.nepaltourismsystem.controller;


import com.example.nepaltourismsystem.*;
import com.example.nepaltourismsystem.utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BookingController {

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> touristColumn;
    @FXML private TableColumn<Booking, String> guideColumn;
    @FXML private TableColumn<Booking, String> attractionColumn;
    @FXML private TableColumn<Booking, String> dateColumn;

    @FXML private ComboBox<Tourist> touristComboBox;
    @FXML private ComboBox<Guide> guideComboBox;
    @FXML private ComboBox<Attraction> attractionComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField costField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button menuButton;

    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        touristColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTourist().getName()));
        guideColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGuide().getName()));
        attractionColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAttraction().getName()));
        dateColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));

        bookingList.addAll(BookingFileHandler.loadBookings());
        bookingTable.setItems(bookingList);

        // Load options into ComboBoxes
        touristComboBox.setItems(FXCollections.observableArrayList(TouristFileHandler.loadTourists()));
        guideComboBox.setItems(FXCollections.observableArrayList(GuideFileHandler.loadGuides()));
        attractionComboBox.setItems(FXCollections.observableArrayList(AttractionFileHandler.loadAttractions()));

        bookingTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> populateFields(newSel));

        addButton.setOnAction(e -> addBooking());
        updateButton.setOnAction(e -> updateBooking());
        deleteButton.setOnAction(e -> deleteBooking());
        clearButton.setOnAction(e -> clearFields());
    }

    private void addBooking() {
        try {
            Tourist tourist = touristComboBox.getValue();
            Guide guide = guideComboBox.getValue();
            Attraction attraction = attractionComboBox.getValue();
            LocalDate date = datePicker.getValue();
            double cost = Double.parseDouble(costField.getText());

            Booking booking = new Booking(tourist, guide, attraction, date, "Pending", cost);
            bookingList.add(booking);
            BookingFileHandler.saveBookings(bookingList);
            clearFields();
        } catch (Exception e) {
            showAlert("Invalid Input", e.getMessage());
        }
    }

    private void updateBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setTourist(touristComboBox.getValue());
                selected.setGuide(guideComboBox.getValue());
                selected.setAttraction(attractionComboBox.getValue());
                selected.setDate(datePicker.getValue());
                selected.setCost(Double.parseDouble(costField.getText()));
                selected.setStatus("Pending");

                bookingTable.refresh();
                BookingFileHandler.saveBookings(bookingList);
                clearFields();
            } catch (Exception e) {
                showAlert("Invalid Input", e.getMessage());
            }
        } else {
            showAlert("No Selection", "Please select a booking to update.");
        }
    }

    private void deleteBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookingList.remove(selected);
            BookingFileHandler.saveBookings(bookingList);
            clearFields();
        } else {
            showAlert("No Selection", "Please select a booking to delete.");
        }
    }

    private void clearFields() {
        touristComboBox.getSelectionModel().clearSelection();
        guideComboBox.getSelectionModel().clearSelection();
        attractionComboBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        costField.clear();
        bookingTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Booking booking) {
        if (booking != null) {
            touristComboBox.setValue(booking.getTourist());
            guideComboBox.setValue(booking.getGuide());
            attractionComboBox.setValue(booking.getAttraction());
            datePicker.setValue(booking.getDate());
            costField.setText(String.valueOf(booking.getCost()));
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent, "/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }
}