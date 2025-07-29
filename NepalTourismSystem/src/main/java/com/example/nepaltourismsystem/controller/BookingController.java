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
import java.util.Locale;
import java.util.ResourceBundle;

public class BookingController {

    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> touristColumn;
    @FXML private TableColumn<Booking, String> guideColumn;
    @FXML private TableColumn<Booking, String> attractionColumn;
    @FXML private TableColumn<Booking, String> dateColumn;
    @FXML private TableColumn<Booking, String> endDateColumn;
    @FXML private TableColumn<Booking, String> costColumn;

    @FXML private ComboBox<Tourist> touristComboBox;
    @FXML private ComboBox<Guide> guideComboBox;
    @FXML private ComboBox<Attraction> attractionComboBox;
    @FXML private DatePicker datePicker; // Start Date
    @FXML private DatePicker endDatePicker; // End Date
    @FXML private TextField costField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button menuButton;

    @FXML private Label touristLabel;
    @FXML private Label guideLabel;
    @FXML private Label attractionLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label costLabel;

    @FXML
    private ComboBox<String> langBox;
    private ResourceBundle bundle;

    private final ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        changeLanguage("en");

        langBox.setOnAction(event -> {
            String selectedLanguage = langBox.getSelectionModel().getSelectedItem();
            if (selectedLanguage.equals("English")) {
                changeLanguage("en");
            } else if (selectedLanguage.equals("Nepali")) {
                changeLanguage("ne");
            }
        });

        touristColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTourist().getName()));
        guideColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGuide().getName()));
        attractionColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAttraction().getName()));
        dateColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDate().toString()));

        endDateColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEndDate().toString()));

        costColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCost().toString()));

        bookingList.addAll(BookingFileHandler.loadBookings());
        bookingTable.setItems(bookingList);

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

    private void changeLanguage(String language) {
        bundle = ResourceBundle.getBundle("lang.messages", new Locale(language));
        touristColumn.setText(bundle.getString("tourist"));
        guideColumn.setText(bundle.getString("guide"));
        attractionColumn.setText(bundle.getString("attraction"));
        dateColumn.setText(bundle.getString("date"));
        endDateColumn.setText(bundle.getString("endDate"));
        costColumn.setText(bundle.getString("cost"));
        touristLabel.setText(bundle.getString("tourist"));
        guideLabel.setText(bundle.getString("guide"));
        attractionLabel.setText(bundle.getString("attraction"));
        startDateLabel.setText(bundle.getString("date"));
        endDateLabel.setText(bundle.getString("endDate"));

        addButton.setText(bundle.getString("add"));
        updateButton.setText(bundle.getString("update"));
        deleteButton.setText(bundle.getString("delete"));
        clearButton.setText(bundle.getString("clear"));
        menuButton.setText(bundle.getString("menu"));

    }

    private void addBooking() {
        try {
            Tourist tourist = touristComboBox.getValue();
            Guide guide = guideComboBox.getValue();
            Attraction attraction = attractionComboBox.getValue();
            LocalDate startDate = datePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            double cost = Double.parseDouble(costField.getText());

            if (startDate == null || endDate == null)
                throw new IllegalArgumentException("Start and end dates must be selected.");
            if (endDate.isBefore(startDate))
                throw new IllegalArgumentException("End date cannot be before start date.");

            // Prevent booking for tourist or guide with a date overlapping
//            if (isDateRangeOverlapping(startDate, endDate, tourist, guide)) {
//                showAlert("Booking Conflict", "The guide or tourist is already booked in this date range.");
//                return;
//            }

            String conflictMessage = getBookingConflict(startDate, endDate, tourist, guide);
            if (conflictMessage != null) {
                showAlert("Booking Conflict", conflictMessage);
                return;
            }

            // Festival discount
            if (isFestivalPeriod(startDate)) {
                cost *= 0.8;
                showInfo("Festival Discount", "A 20% festival discount has been applied!");
            }

            // Altitude warning
            if (attraction != null && attraction.getAltitude() > 3000) {
                showAlert("Altitude Warning", "This attraction is above 3000m. It may be hazardous to health.");
            }

            Booking booking = new Booking(tourist, guide, attraction, startDate, endDate, "Pending", cost);
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
                Tourist tourist = touristComboBox.getValue();
                Guide guide = guideComboBox.getValue();
                Attraction attraction = attractionComboBox.getValue();
                LocalDate startDate = datePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();
                double cost = Double.parseDouble(costField.getText());

                if (startDate == null || endDate == null)
                    throw new IllegalArgumentException("Start and end dates must be selected.");
                if (endDate.isBefore(startDate))
                    throw new IllegalArgumentException("End date cannot be before start date.");

                // Prevent booking for tourist or guide with a date overlapping
                String conflictMessage = getBookingConflict(startDate, endDate, tourist, guide);
                if (conflictMessage != null) {
                    showAlert("Booking Conflict", conflictMessage);
                    return;
                }


                // Festival discount
                if (isFestivalPeriod(startDate)) {
                    double discount = cost * (1 /5);
                    cost = cost - discount;
                    showInfo("Festival Discount", "A 20% festival discount has been applied!");
                }

                // Altitude warning
                if (attraction != null && attraction.getAltitude() > 3000) {
                    showAlert("Altitude Warning", "This attraction is above 3000m. It may be hazardous to health.");
                }

                selected.setTourist(tourist);
                selected.setGuide(guide);
                selected.setAttraction(attraction);
                selected.setDate(startDate);
                selected.setEndDate(endDate);
                selected.setCost(cost);
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
        endDatePicker.setValue(null);
        costField.clear();
        bookingTable.getSelectionModel().clearSelection();
    }

    private void populateFields(Booking booking) {
        if (booking != null) {
            touristComboBox.setValue(booking.getTourist());
            guideComboBox.setValue(booking.getGuide());
            attractionComboBox.setValue(booking.getAttraction());
            datePicker.setValue(booking.getDate());
            endDatePicker.setValue(booking.getEndDate());
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

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean isFestivalPeriod(LocalDate date) {
        return date != null && (date.getMonthValue() == 10 || date.getMonthValue() == 11);
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent, "/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }

    private String getBookingConflict(LocalDate newStart, LocalDate newEnd, Tourist tourist, Guide guide) {
        for (Booking b : bookingList) {
            if (b.getDate() == null || b.getEndDate() == null) continue;
            LocalDate bookedStart = b.getDate();
            LocalDate bookedEnd = b.getEndDate();

            boolean overlap = !(newEnd.isBefore(bookedStart) || newStart.isAfter(bookedEnd));

            if (overlap) {
                if (b.getTourist().equals(tourist)) {
                    return "The selected tourist is already booked from " + bookedStart + " to " + bookedEnd + ".";
                }
                if (b.getGuide().equals(guide)) {
                    return "The selected guide is already booked from " + bookedStart + " to " + bookedEnd + ".";
                }
            }
        }
        return null;
    }
}
