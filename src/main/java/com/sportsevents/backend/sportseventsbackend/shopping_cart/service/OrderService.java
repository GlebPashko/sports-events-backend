package com.sportsevents.backend.sportseventsbackend.shopping_cart.service;


import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order.CreateOrderRequestDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order.OrderItemResponseDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order.OrderResponseDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order.UpdateOrderRequestDto;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(CreateOrderRequestDto requestDto);

    Set<OrderResponseDto> getOrders(Pageable pageable);

    OrderResponseDto updateOrderStatus(Long id, UpdateOrderRequestDto requestDto);

    Set<OrderItemResponseDto> getOrdersItems(Long orderId);

    OrderItemResponseDto getOrdersItem(Long orderId, Long itemId);
}
