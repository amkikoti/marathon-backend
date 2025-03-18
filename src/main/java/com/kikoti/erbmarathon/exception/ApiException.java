package com.kikoti.erbmarathon.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiException {
    private String message;

    public ApiException(String message) {
        this.message = message;
    }

}
