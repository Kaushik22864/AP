package com.example.nepaltourismsystem;

import java.util.Map;

public class Reports {
    private int totalTourists;
    private int totalGuides;
    private int totalAttractions;
    private int totalBookings;
    private Map<String, Integer> bookingDistributionByAttraction;

    public Reports(int totalTourists, int totalGuides, int totalAttractions, int totalBookings, Map<String, Integer> bookingDistributionByAttraction) {
        this.totalTourists = totalTourists;
        this.totalGuides = totalGuides;
        this.totalAttractions = totalAttractions;
        this.totalBookings = totalBookings;
        this.bookingDistributionByAttraction = bookingDistributionByAttraction;
    }

    public int getTotalTourists() {
        return totalTourists;
    }

    public void setTotalTourists(int totalTourists) {
        this.totalTourists = totalTourists;
    }

    public int getTotalGuides() {
        return totalGuides;
    }

    public void setTotalGuides(int totalGuides) {
        this.totalGuides = totalGuides;
    }

    public int getTotalAttractions() {
        return totalAttractions;
    }

    public void setTotalAttractions(int totalAttractions) {
        this.totalAttractions = totalAttractions;
    }

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Map<String, Integer> getBookingDistributionByAttraction() {
        return bookingDistributionByAttraction;
    }

    public void setBookingDistributionByAttraction(Map<String, Integer> bookingDistributionByAttraction) {
        this.bookingDistributionByAttraction = bookingDistributionByAttraction;
    }

    @Override
    public String toString() {
        return "Reports Summary:\n" +
                "- Tourists: " + totalTourists + "\n" +
                "- Guides: " + totalGuides + "\n" +
                "- Attractions: " + totalAttractions + "\n" +
                "- Bookings: " + totalBookings + "\n" +
                "- Booking Distribution: " + bookingDistributionByAttraction;
    }
}