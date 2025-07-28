package com.example.nepaltourismsystem;

public class Attraction {
    private String name;
    private String type;
    private String location;
    private String description;
    private Double altitude;

    public Attraction(String name, String type, String location, String description, Double altitude) throws EmptyFieldException {
        setName(name);
        setType(type);
        setLocation(location);
        setDescription(description);
        setAltitude(altitude);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Double getAltitude(){return altitude;}

    public void setName(String name) throws EmptyFieldException {
        if (name == null || name.isBlank()) throw new EmptyFieldException("Attraction Name");
        this.name = name;
    }

    public void setType(String type) throws EmptyFieldException {
        if (type == null || type.isBlank()) throw new EmptyFieldException("Attraction Type");
        this.type = type;
    }

    public void setLocation(String location) throws EmptyFieldException {
        if (location == null || location.isBlank()) throw new EmptyFieldException("Attraction Location");
        this.location = location;
    }

    public void setDescription(String description) throws EmptyFieldException {
        if (description == null || description.isBlank()) throw new EmptyFieldException("Attraction Description");
        this.description = description;
    }

    public void setAltitude(Double altitude) {
        if (altitude == null || altitude < 0) {
            this.altitude = 0.0;
        } else {
            this.altitude = altitude;
        }
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}
