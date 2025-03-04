package com.kikoti.erbmarathon.dtos;

import com.kikoti.erbmarathon.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private UserRole userRole;

    private Long userId;

}
