package com.sportsevents.backend.sportseventsbackend.util;

import static com.sportsevents.backend.sportseventsbackend.util.EventTestUtil.getEventDto;

import com.sportsevents.backend.sportseventsbackend.cart.dto.order.CreateOrderRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.order.OrderItemResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.order.OrderResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderTestUtil {
    public static CreateOrderRequestDto getCreateOrderRequestDto() {
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto();
        createOrderRequestDto.setShippingAddress("Shipping Address");
        return createOrderRequestDto;
    }

    public static OrderResponseDto getOrderResponseDto() {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(1L);
        orderResponseDto.setUserId(1L);
        orderResponseDto.setOrderItems(Set.of(getOrderItemResponseDto()));
        orderResponseDto.setOrderDate(LocalDateTime.now());
        orderResponseDto.setTotal(BigDecimal.TEN);
        orderResponseDto.setStatus(Order.Status.PENDING);
        return orderResponseDto;
    }

    public static OrderItemResponseDto getOrderItemResponseDto() {
        OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
        orderItemResponseDto.setId(1L);
        orderItemResponseDto.setEventId(getEventDto().getId());
        orderItemResponseDto.setEventTitle(getEventDto().getTitle());
        orderItemResponseDto.setDescriptionSmall(getEventDto().getDescriptionSmall());
        orderItemResponseDto.setAvatarImage(getEventDto().getAvatarImage());
        orderItemResponseDto.setPrice(getEventDto().getPrice().toString());
        orderItemResponseDto.setCity(getEventDto().getCity());
        orderItemResponseDto.setDateOfStartEvent(getEventDto().getDateOfStartEvent());
        orderItemResponseDto.setQuantity(1);
        return orderItemResponseDto;
    }
}
