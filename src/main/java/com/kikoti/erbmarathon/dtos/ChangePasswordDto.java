package com.kikoti.erbmarathon.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class ChangePasswordDto {

    @NotNull(message = "old password must not be null")
    private String oldPassword;
    @NotNull(message = "new password must not be null")
    private String newPassword;
    @NotNull(message = "confirm password must not be null")
    private String confirmPassword;
}
