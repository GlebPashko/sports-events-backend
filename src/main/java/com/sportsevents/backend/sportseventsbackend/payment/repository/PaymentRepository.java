package com.sportsevents.backend.sportseventsbackend.payment.repository;

import com.sportsevents.backend.sportseventsbackend.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
