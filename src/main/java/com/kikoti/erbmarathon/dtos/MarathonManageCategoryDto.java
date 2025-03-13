package com.kikoti.erbmarathon.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MarathonManageCategoryDto {

    @NotNull(message = "Event id is required")
    private Long marathon;
    @NotEmpty(message = "List of categories cannot be empty")
    private List<Long> categories;
    @NotNull(message = "Action type is required")
    private boolean action;

}
