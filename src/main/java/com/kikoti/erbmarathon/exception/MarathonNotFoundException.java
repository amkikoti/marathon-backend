package com.kikoti.erbmarathon.exception;

public class MarathonNotFoundException extends RuntimeException {
    public MarathonNotFoundException(String message) {
        super(message);
    }
}