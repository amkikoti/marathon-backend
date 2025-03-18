package com.kikoti.erbmarathon.dtos;

import com.kikoti.erbmarathon.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDto {

    @NotNull(message = "User email is required")
    private String email;

    @NotNull(message = "User status is required")
    private UserStatus status;
}