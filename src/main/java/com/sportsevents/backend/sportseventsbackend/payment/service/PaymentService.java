package com.sportsevents.backend.sportseventsbackend.payment.service;

import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentDto;
import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentRequestDto;

public interface PaymentService {
    PaymentDto payForOrder(PaymentRequestDto requestDto);
}
