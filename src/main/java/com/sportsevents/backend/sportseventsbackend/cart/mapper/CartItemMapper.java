package com.sportsevents.backend.sportseventsbackend.cart.mapper;

import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.model.CartItem;
import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.event.mapper.EventMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = EventMapper.class)
public interface CartItemMapper {
    @Mapping(target = "event", source = "eventId", qualifiedByName = "eventFromId")
    CartItem toModel(CartItemRequestDto requestDto);

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "event.title", target = "eventTitle")
    @Mapping(source = "event.descriptionSmall", target = "descriptionSmall")
    @Mapping(source = "event.avatarImage", target = "avatarImage")
    @Mapping(source = "event.price", target = "price")
    @Mapping(source = "event.city", target = "city")
    @Mapping(source = "event.dateOfStartEvent", target = "dateOfStartEvent")
    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);
}
