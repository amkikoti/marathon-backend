package com.kikoti.erbmarathon.exception;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(String message) {
        super(message);
    }
}