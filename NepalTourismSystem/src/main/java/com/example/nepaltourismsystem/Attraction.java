package com.example.nepaltourismsystem;

public class Attraction {
    private String name;
    private String type;
    private String location;
    private String description;

    public Attraction(String name, String type, String location, String description) throws EmptyFieldException {
        setName(name);
        setType(type);
        setLocation(location);
        setDescription(description);
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

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}
