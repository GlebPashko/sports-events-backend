package com.sportsevents.backend.sportseventsbackend.cart.mapper;

import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.cart.model.ShoppingCart;
import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart);
}
