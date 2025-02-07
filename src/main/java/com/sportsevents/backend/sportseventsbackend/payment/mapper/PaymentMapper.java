package com.sportsevents.backend.sportseventsbackend.payment.mapper;

import com.sportsevents.backend.sportseventsbackend.cart.model.Order;
import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.payment.dto.PaymentDto;
import com.sportsevents.backend.sportseventsbackend.payment.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "amount", source = "total")
    @Mapping(target = "status", source = "status")
    PaymentDto toPaymentDto(Order order);

    Payment toModel(PaymentDto paymentDto);
}
