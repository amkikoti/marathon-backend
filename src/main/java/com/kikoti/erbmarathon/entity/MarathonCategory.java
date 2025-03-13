package com.kikoti.erbmarathon.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "marathon_category")
public class MarathonCategory {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "Title is required")
    private String categoryTitle;
    @NotNull(message = "Length is required")
    private Long categoryLength;
    @NotNull(message = "Measure is required")
    private String categoryMeasure;
    @NotNull(message = "Entry is required")
    private Double categoryEntry;
    @NotNull(message = "Limit is required")
    private Long categoryLimit;
}
