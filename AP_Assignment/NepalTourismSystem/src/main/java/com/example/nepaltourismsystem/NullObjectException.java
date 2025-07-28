package com.example.nepaltourismsystem;

public class NullObjectException extends InvalidDataException {
    public NullObjectException(String objectName) {
        super(objectName + " must not be null.");
    }
}