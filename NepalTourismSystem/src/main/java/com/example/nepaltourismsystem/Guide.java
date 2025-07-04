package com.example.nepaltourismsystem;

import java.util.List;

public class Guide {
    private String name;
    private List<String> languages;
    private int experienceYears;
    private String contact;

    public Guide(String name, List<String> languages, int experienceYears, String contact)
            throws EmptyFieldException, NullObjectException, NegativeValueException {
        setName(name);
        setLanguages(languages);
        setExperienceYears(experienceYears);
        setContact(contact);
    }

    public String getName() {
        return name;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) throws EmptyFieldException {
        if (name == null || name.isBlank()) throw new EmptyFieldException("Guide Name");
        this.name = name;
    }

    public void setLanguages(List<String> languages) throws NullObjectException {
        if (languages == null || languages.isEmpty()) throw new NullObjectException("Languages");
        this.languages = languages;
    }

    public void setExperienceYears(int experienceYears) throws NegativeValueException {
        if (experienceYears < 0) throw new NegativeValueException("Experience Years");
        this.experienceYears = experienceYears;
    }

    public void setContact(String contact) throws EmptyFieldException {
        if (contact == null || contact.isBlank()) throw new EmptyFieldException("Guide Contact");
        this.contact = contact;
    }

    @Override
    public String toString() {
        return name + " (" + experienceYears + " yrs)";
    }
}
