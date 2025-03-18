package com.kikoti.erbmarathon.entity;

import com.kikoti.erbmarathon.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MarathonPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDate;
    private LocalTime paymentTime;
    private Double paymentAmount;
    private String paymentReference;
    private String paymentChannel;
    private String paymentChannelName;
    private PaymentStatus paymentStatus;
}
