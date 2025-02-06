package com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart;

import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemResponseDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
