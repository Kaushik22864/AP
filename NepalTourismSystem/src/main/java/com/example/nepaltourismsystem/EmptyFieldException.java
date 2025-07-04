package com.example.nepaltourismsystem;

public class EmptyFieldException extends InvalidDataException {
    public EmptyFieldException(String fieldName) {
        super(fieldName + " cannot be empty.");
    }
}
