package com.sportsevents.backend.sportseventsbackend.cart.service.impl;

import com.sportsevents.backend.sportseventsbackend.cart.dto.order.CreateOrderRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.order.OrderItemResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.order.OrderResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.order.UpdateOrderRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.mapper.OrderItemMapper;
import com.sportsevents.backend.sportseventsbackend.cart.mapper.OrderMapper;
import com.sportsevents.backend.sportseventsbackend.cart.model.Order;
import com.sportsevents.backend.sportseventsbackend.cart.model.OrderItem;
import com.sportsevents.backend.sportseventsbackend.cart.model.ShoppingCart;
import com.sportsevents.backend.sportseventsbackend.cart.repository.order.OrderRepository;
import com.sportsevents.backend.sportseventsbackend.cart.repository.orderitem.OrderItemRepository;
import com.sportsevents.backend.sportseventsbackend.cart.repository.shoppingcart.ShoppingCartRepository;
import com.sportsevents.backend.sportseventsbackend.cart.service.OrderService;
import com.sportsevents.backend.sportseventsbackend.event.repository.eventparticipant.EventParticipantRepository;
import com.sportsevents.backend.sportseventsbackend.payment.service.PaymentService;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        User user = getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());

        Set<OrderItem> orderItems = orderItemMapper.toOrderItemSet(shoppingCart.getCartItems());
        orderItems.forEach(orderItem -> validateAndSetOrderItem(orderItem, order));

        order.setOrderItems(orderItems);
        order.setTotal(
                orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        shoppingCart.getCartItems().clear();
        orderRepository.save(order);

        return orderMapper.orderResponseDto(order);
    }

    @Override
    public Set<OrderResponseDto> getOrders(Pageable pageable) {
        User user = getAuthenticatedUser();
        Page<Order> order = orderRepository.findByUserId(user.getId(), pageable);
        orderMapper.orderResponseDtoSet(order);

        return orderMapper.orderResponseDtoSet(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order not found"));
        order.setStatus(Order.Status.valueOf(requestDto.getStatus()));

        return orderMapper.orderResponseDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderItemResponseDto> getOrdersItems(Long orderId) {
        Set<OrderItem> orders = orderItemRepository.findByOrderId(orderId);
        return orderItemMapper.toOrderItemResponseDtoSet(orders);
    }

    @Override
    public OrderItemResponseDto getOrdersItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndOrderItemId(orderId, itemId);
        return orderItemMapper.toOrderItemResponseDto(orderItem);
    }

    private void validateAndSetOrderItem(OrderItem orderItem, Order order) {
        if (orderItem.getPrice() == null || orderItem.getQuantity() < 1) {
            throw new IllegalArgumentException("OrderItem with id "
                    + orderItem.getId() + " has an illegal price or quantity.");
        }
        orderItem.setOrder(order);
        orderItem.setPrice(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
    }

    private User getAuthenticatedUser() {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal().toString();
        return userRepository.findByEmail(userEmail).orElseThrow();
    }
}
