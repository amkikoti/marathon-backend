package com.kikoti.erbmarathon.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;

}