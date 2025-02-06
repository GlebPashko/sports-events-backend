package com.sportsevents.backend.sportseventsbackend.shopping_cart.mapper;


import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order.OrderResponseDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.model.Order;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto orderResponseDto(Order order);

    @Mapping(target = "orderItems", source = "orderItems")
    Set<OrderResponseDto> orderResponseDtoSet(Page<Order> order);
}
