package com.kikoti.erbmarathon.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class MarathonDto {
    private Long id;
    @NotNull(message = "Title is required")
    private String marathonTitle;
    @NotNull(message = "Description is required")
    private String marathonDescription;
    @NotNull(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate marathonStartDate;
    @NotNull(message = "Time is required")
    private LocalTime marathonStartTime;
    @NotNull(message = "Country is required")
    private String marathonCountry;
    @NotNull(message = "Region is required")
    private String marathonRegion;
    @NotNull(message = "Year is required")
    private String marathonYear;

    private List<Long> categories;
}
