package com.sportsevents.backend.sportseventsbackend.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDto {
    @NotNull
    private Long orderId;

    @NotBlank
    private String paymentMethod;
}
