package com.kikoti.erbmarathon.exception;

public class RegistrationClosedException extends RuntimeException {
    public RegistrationClosedException(String message) {
        super(message);
    }
}