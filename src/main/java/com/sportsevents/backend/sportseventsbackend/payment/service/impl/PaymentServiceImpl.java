package com.sportsevents.backend.sportseventsbackend.payment.service.impl;

import com.liqpay.LiqPay;
import com.sportsevents.backend.sportseventsbackend.cart.model.Order;
import com.sportsevents.backend.sportseventsbackend.cart.repository.order.OrderRepository;
import com.sportsevents.backend.sportseventsbackend.event.model.EventParticipant;
import com.sportsevents.backend.sportseventsbackend.event.repository.eventparticipant.EventParticipantRepository;
import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentDto;
import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentRequestDto;
import com.sportsevents.backend.sportseventsbackend.payment.mapper.PaymentMapper;
import com.sportsevents.backend.sportseventsbackend.payment.model.Payment;
import com.sportsevents.backend.sportseventsbackend.payment.repository.PaymentRepository;
import com.sportsevents.backend.sportseventsbackend.payment.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService { // LiqPay
    private static final String PUBLIC_KEY = "sandbox_i97674120460";
    private static final String PRIVATE_KEY = "sandbox_B7ew9G3Gutb61cx3XuVoOcJQQUP3ny5JonNTp0cK";

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentDto payForOrder(PaymentRequestDto requestDto) {
        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Map<String, String> params = new HashMap<>();
        params.put("version", "3");
        params.put("action", "pay");
        params.put("public_key", PUBLIC_KEY);
        params.put("amount", order.getTotal().toString());
        params.put("currency", "UAH");
        params.put("description", "Оплата за замовлення № : " + order.getId());
        params.put("order_id", order.getId().toString());
        params.put("sandbox", "1"); // Test

        LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
        PaymentDto paymentDto = paymentMapper.toPaymentDto(order);
        paymentDto.setHtmlForm(liqpay.cnb_form(params));
        paymentDto.setPaymentMethod(requestDto.getPaymentMethod());
        paymentDto.setPaymentDate(LocalDateTime.now());

        order.setStatus(Order.Status.PAID);
        orderRepository.save(order);

        Payment payment = paymentMapper.toModel(paymentDto);
        payment.setStatus(Payment.PaymentStatus.SUCCESS);
        payment.setOrder(order);
        paymentRepository.save(payment);

        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setEvent(order.getOrderItems()
                .stream().findFirst().get().getEvent()); // TODO: need no improve logic in future
        eventParticipant.setUser(order.getUser());
        eventParticipant.setOrder(order);
        eventParticipantRepository.save(eventParticipant);
        return paymentDto;
    }
}
