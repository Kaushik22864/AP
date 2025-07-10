package com.example.nepaltourismsystem;

public class Tourist {
    private String name;
    private String nationality;
    private String contact;
    private String emergencyContact;

    public Tourist(String name, String nationality, String contact, String emergencyContact) throws EmptyFieldException {
        setName(name);
        setNationality(nationality);
        setContact(contact);
        setEmergencyContact(emergencyContact);
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getContact() {
        return contact;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setName(String name) throws EmptyFieldException {
        if (name == null || name.isBlank()) throw new EmptyFieldException("Name");
        this.name = name;
    }

    public void setNationality(String nationality) throws EmptyFieldException {
        if (nationality == null || nationality.isBlank()) throw new EmptyFieldException("Nationality");
        this.nationality = nationality;
    }

    public void setContact(String contact) throws EmptyFieldException {
        if (contact == null || contact.isBlank()) throw new EmptyFieldException("Contact");
        this.contact = contact;
    }

    public void setEmergencyContact(String emergencyContact) throws EmptyFieldException {
        if (emergencyContact == null || emergencyContact.isBlank()) throw new EmptyFieldException("Emergency Contact");
        this.emergencyContact = emergencyContact;
    }

    @Override
    public String toString() {
        return name + " (" + nationality + ")";
    }
}