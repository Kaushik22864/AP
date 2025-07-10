package com.example.nepaltourismsystem.controller;

import com.example.nepaltourismsystem.Booking;
import com.example.nepaltourismsystem.Reports;
import com.example.nepaltourismsystem.utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsController {

    @FXML private Label totalTouristsLabel;
    @FXML private Label totalGuidesLabel;
    @FXML private Label totalAttractionsLabel;
    @FXML private Label totalBookingsLabel;
    @FXML private PieChart bookingPieChart;
    @FXML private Button menuButton;

    public void generateAndSetReport(int totalTourists, int totalGuides, int totalAttractions, List<Booking> bookings) {
        int totalBookings = bookings.size();
        Map<String, Integer> distribution = calculateAttractionDistribution(bookings);

        Reports report = new Reports(
                totalTourists,
                totalGuides,
                totalAttractions,
                totalBookings,
                distribution
        );

        setReport(report);
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

    public void returnMain(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        utility.changeScene(actionEvent,"/com/example/nepaltourismsystem/Main.fxml", "Main Page");
    }
}

//controller.generateAndSetReport(
//        tourists.size(),
//    guides.size(),
//    attractions.size(),
//bookings
//);
//
//In MainController.java (or your main navigation handler):
//@FXML
//private void handleReportButtonClick() {
//    try {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nepaltourismsystem/view/ReportsView.fxml"));
//        Parent root = loader.load();
//
//        // Get controller
//        ReportsController controller = loader.getController();
//
//        // Pass in data (your real lists from memory or files)
//        controller.generateAndSetReport(
//                touristList.size(),
//                guideList.size(),
//                attractionList.size(),
//                bookingList
//        );
//
//        // Switch to Reports view
//        Stage stage = (Stage) reportButton.getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.setTitle("Reports and Analytics");
//        stage.show();
//
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}

