package com.sportsevents.backend.sportseventsbackend.cart.mapper;

import com.sportsevents.backend.sportseventsbackend.cart.dto.order.OrderItemResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.model.CartItem;
import com.sportsevents.backend.sportseventsbackend.cart.model.OrderItem;
import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "event.price")
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(source = "event.title", target = "eventTitle")
    @Mapping(source = "event.descriptionSmall", target = "descriptionSmall")
    @Mapping(source = "event.avatarImage", target = "avatarImage")
    @Mapping(source = "event.price", target = "price")
    @Mapping(source = "event.city", target = "city")
    @Mapping(source = "event.dateOfStartEvent", target = "dateOfStartEvent")
    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

    Set<OrderItem> toOrderItemSet(Set<CartItem> cartItems);

    Set<OrderItemResponseDto> toOrderItemResponseDtoSet(Set<OrderItem> orderItems);
}
