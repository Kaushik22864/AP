package com.example.nepaltourismsystem;

public class NegativeValueException extends InvalidDataException {
    public NegativeValueException(String fieldName) {
        super(fieldName + " cannot be negative.");
    }
}