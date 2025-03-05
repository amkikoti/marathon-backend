package com.kikoti.erbmarathon.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarathonRegistrationDto {

    @NotNull(message = "Marathon ID is required")
    private Long marathonId;

    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;

}