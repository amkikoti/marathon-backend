package com.kikoti.erbmarathon.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseWrapper<T> {
    private T data;
    private String message;
    private boolean success;
}
