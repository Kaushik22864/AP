package com.example.nepaltourismsystem.controller;


import com.example.nepaltourismsystem.*;
import com.example.nepaltourismsystem.utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReportsController {

    @FXML private Label totalTouristsLabel;
    @FXML private Label totalGuidesLabel;
    @FXML private Label totalAttractionsLabel;
    @FXML private Label totalBookingsLabel;

    @FXML private PieChart bookingPieChart;
    @FXML private BarChart<String, Number> guideBookingBarChart;

    @FXML private Button menuButton;
    @FXML private Button exportPdfButton;

    @FXML private Label bookingsPerGuide;
    @FXML private Label bookingDistributionByAttraction;

    @FXML
    private ComboBox<String> langBox;
    private ResourceBundle bundle;

    private Reports currentReport;

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

        List<Tourist> tourists = TouristFileHandler.loadTourists();
        List<Guide> guides = GuideFileHandler.loadGuides();
        List<Attraction> attractions = AttractionFileHandler.loadAttractions();
        List<Booking> bookings = BookingFileHandler.loadBookings();

        generateAndSetReport(
                tourists.size(),
                guides.size(),
                attractions.size(),
                bookings
        );

        populateGuideBookingChart(bookings);

        exportPdfButton.setOnAction(e -> exportToPDF());
    }

    private void changeLanguage(String language) {
        bundle = ResourceBundle.getBundle("lang.messages", new Locale(language));
        exportPdfButton.setText(bundle.getString("exportToPdf"));
        bookingDistributionByAttraction.setText(bundle.getString("bookingDistributionByAttraction"));
        bookingsPerGuide.setText(bundle.getString("bookingsPerGuide"));
        menuButton.setText(bundle.getString("menu"));
    }

    public void generateAndSetReport(int totalTourists, int totalGuides, int totalAttractions, List<Booking> bookings) {
        int totalBookings = bookings.size();
        Map<String, Integer> distribution = calculateAttractionDistribution(bookings);

        currentReport = new Reports(
                totalTourists,
                totalGuides,
                totalAttractions,
                totalBookings,
                distribution
        );

        setReport(currentReport);
    }

    public void setReport(Reports report) {
        if (report == null) return;

        totalTouristsLabel.setText("Total Tourists: " + report.getTotalTourists());
        totalGuidesLabel.setText("Total Guides: " + report.getTotalGuides());
        totalAttractionsLabel.setText("Total Attractions: " + report.getTotalAttractions());
        totalBookingsLabel.setText("Total Bookings: " + report.getTotalBookings());

        bookingPieChart.getData().clear();
        for (Map.Entry<String, Integer> entry : report.getBookingDistributionByAttraction().entrySet()) {
            bookingPieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }

    private Map<String, Integer> calculateAttractionDistribution(List<Booking> bookings) {
        Map<String, Integer> map = new HashMap<>();
        for (Booking booking : bookings) {
            String name = booking.getAttraction().getName();
            map.put(name, map.getOrDefault(name, 0) + 1);
        }
        return map;
    }

    private void populateGuideBookingChart(List<Booking> bookings) {
        Map<String, Integer> guideCount = new HashMap<>();

        for (Booking booking : bookings) {
            String guideName = booking.getGuide().getName();
            guideCount.put(guideName, guideCount.getOrDefault(guideName, 0) + 1);
        }

        guideBookingBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings per Guide");

        for (Map.Entry<String, Integer> entry : guideCount.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        guideBookingBarChart.getData().add(series);
    }
    @FXML private Node rootContainer; // Set this in FXML as fx:id="rootContainer"
    private void exportToPDF() {
        try {
            String filePath = "Reports/ReportView_" + System.currentTimeMillis() + ".pdf";
            new File("Reports").mkdirs();

            PDFExportUtil.exportNodeAsPdf(rootContainer, filePath);

            showAlert("Exported", "Visual report exported to:\n" + filePath);
        } catch (Exception e) {
            showAlert("Error", "Failed to export: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void returnMain(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }
}