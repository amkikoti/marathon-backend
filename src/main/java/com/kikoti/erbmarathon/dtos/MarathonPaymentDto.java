package com.kikoti.erbmarathon.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kikoti.erbmarathon.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class MarathonPaymentDto {
    private Long id;
    @NotNull(message = "Payment date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate paymentDate;
    @NotNull(message = "Payment time is required")
    private LocalTime paymentTime;
    @NotNull(message = "Payment amount is required")
    private Double paymentAmount;
    @NotNull(message = "Payment reference is required")
    private String paymentReference;
    @NotNull(message = "Payment channel is required")
    private String paymentChannel;
    @NotNull(message = "Payment channel name is required")
    private String paymentChannelName;
    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}
