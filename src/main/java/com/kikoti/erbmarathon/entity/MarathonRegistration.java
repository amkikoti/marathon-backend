package com.kikoti.erbmarathon.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "marathon_registrations")
public class MarathonRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "marathon_id", nullable = false)
    private Marathon marathon;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String emergencyContact;

    @CreationTimestamp
    private LocalDateTime registrationDate;


}