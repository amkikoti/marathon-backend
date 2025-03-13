package com.kikoti.erbmarathon.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "marathons")
public class Marathon {


    @Id
    @GeneratedValue
    private Long id;
    private String marathonTitle;
    private String marathonDescription;
    private LocalDate marathonStartDate;
    private LocalTime marathonStartTime;
    private String marathonCountry;
    private String marathonRegion;
    private String marathonYear;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    private List<MarathonCategory> categories;

    public void addCategory(MarathonCategory category) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(category);
    }

}
