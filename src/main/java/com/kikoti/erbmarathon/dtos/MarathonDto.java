package com.kikoti.erbmarathon.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class MarathonDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Time is required")
    private LocalTime time;

    private String description;

    @NotNull(message = "Max participants is required")
    private int maxParticipants;

    private boolean isActive = true; //hii ni default value

    @NotNull(message = "Registration fee is required")
    private double registrationFee;
}
