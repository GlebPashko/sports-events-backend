package com.sportsevents.backend.sportseventsbackend.payment.controller;

import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentDto;
import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentRequestDto;
import com.sportsevents.backend.sportseventsbackend.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment management", description = "Endpoints for managing payment")
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Pay for Order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/pay")
    public PaymentDto payForOrder(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.payForOrder(requestDto);
    }

}
