package com.kikoti.erbmarathon.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmResetPasswordDto {

    @NotNull(message = "email must not be null")
    private String email;
    @NotNull(message = "password must not be null")
    private String password;
    @NotNull(message = "confirm password")
    private String confirmPassword;
    @NotNull(message = "confirmation code must not be null")
    private Integer confirmationCode;
}