package com.sportsevents.backend.sportseventsbackend.shopping_cart.mapper;


import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart.ShoppingCartResponseDto;
import com.sportsevents.backend.sportseventsbackend.shopping_cart.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart);
}
