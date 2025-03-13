package com.kikoti.erbmarathon.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarathonCategoryDto {
    private Long id;
    private String categoryTitle;
    private Long categoryLength;
    private String categoryMeasure;
    private Double categoryEntry;
    private Long categoryLimit;
}
