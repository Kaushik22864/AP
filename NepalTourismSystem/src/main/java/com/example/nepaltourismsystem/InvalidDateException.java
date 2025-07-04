package com.example.nepaltourismsystem;

import java.time.LocalDate;

public class InvalidDateException extends InvalidDataException {
    public InvalidDateException(LocalDate date) {
        super("Invalid booking date: " + date);
    }
}