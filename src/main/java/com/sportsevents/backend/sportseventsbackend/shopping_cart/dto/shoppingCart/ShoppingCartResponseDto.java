package com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart;

import com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.cartItem.CartItemResponseDto;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
