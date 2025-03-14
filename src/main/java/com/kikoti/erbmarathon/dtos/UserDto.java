package com.kikoti.erbmarathon.dtos;

import com.kikoti.erbmarathon.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

}
