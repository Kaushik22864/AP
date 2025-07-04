package com.example.nepaltourismsystem;

public class Attraction {
    private String name;
    private String type;
    private String location;
    private String difficulty;

    public Attraction(String name, String type, String location, String difficulty) throws EmptyFieldException {
        setName(name);
        setType(type);
        setLocation(location);
        setDifficulty(difficulty);
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

    public String getDifficulty() {
        return difficulty;
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

    public void setDifficulty(String difficulty) throws EmptyFieldException {
        if (difficulty == null || difficulty.isBlank()) throw new EmptyFieldException("Attraction Difficulty");
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}

