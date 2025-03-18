package com.kikoti.erbmarathon.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kikoti.erbmarathon.entity.user.User;
import com.kikoti.erbmarathon.enums.EntryType;
import com.kikoti.erbmarathon.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "marathon_registration")
public class MarathonRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationDate;
    private String registrationNumber;
    private String registrationSize;
    private String registrationPhoneNumber;
    private String registrationIdNumber;
    private String registrationPassportNumber;
    private String registrationEmergencyContact;
    private String registrationEmergencyNumber;
    private String registrationAddress;
    private String registrationCountry;
    private Long registrationAge;

    @Enumerated(EnumType.STRING)
    private Gender registrationGender;

    @Enumerated(EnumType.STRING)
    private EntryType registrationEntry;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private MarathonPayment registrationPayment;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User registeredUser;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "marathon_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marathon registrationMarathon;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "marathon_category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MarathonCategory registrationCategory;

}