package com.kikoti.erbmarathon.exception;

public class MarathonFullException extends RuntimeException {
    public MarathonFullException(String message) {
        super(message);
    }
}