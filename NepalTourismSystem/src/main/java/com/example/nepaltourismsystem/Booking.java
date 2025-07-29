package com.example.nepaltourismsystem;

import java.time.LocalDate;

public class Booking {
    private Tourist tourist;
    private Guide guide;
    private Attraction attraction;
    private LocalDate date;
    private LocalDate endDate;
    private String status;
    private double cost;

    public Booking(Tourist tourist, Guide guide, Attraction attraction, LocalDate date, LocalDate endDate, String status, double cost)
            throws NullObjectException, InvalidDateException {
        setTourist(tourist);
        setGuide(guide);
        setAttraction(attraction);
        setDate(date);
        setEndDate(endDate);
        setStatus(status);
        setCost(cost);
    }

    public Tourist getTourist() {
        return tourist;
    }

    public Guide getGuide() {
        return guide;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public Double getCost() {
        return cost;
    }

    public void setTourist(Tourist tourist) throws NullObjectException {
        if (tourist == null) throw new NullObjectException("Tourist");
        this.tourist = tourist;
    }

    public void setGuide(Guide guide) throws NullObjectException {
        if (guide == null) throw new NullObjectException("Guide");
        this.guide = guide;
    }

    public void setAttraction(Attraction attraction) throws NullObjectException {
        if (attraction == null) throw new NullObjectException("Attraction");
        this.attraction = attraction;
    }

    public void setDate(LocalDate date) throws InvalidDateException {
        if (date == null || date.isBefore(LocalDate.now()))
            throw new InvalidDateException(date);
        this.date = date;
    }

    public void setEndDate(LocalDate endDate) throws InvalidDateException {
        if (endDate == null || (this.date != null && endDate.isBefore(this.date)))
            throw new InvalidDateException(endDate);
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = (status == null || status.isBlank()) ? "Pending" : status;
    }

    public void setCost(double cost) {
        this.cost = (cost < 0) ? 0 : cost;
    }

    @Override
    public String toString() {
        return tourist.getName() + " → " + attraction.getName() + " (" + date + " to " + endDate + ")";
    }
}
