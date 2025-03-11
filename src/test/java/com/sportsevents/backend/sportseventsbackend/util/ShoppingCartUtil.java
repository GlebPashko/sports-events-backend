package com.sportsevents.backend.sportseventsbackend.util;

import static com.sportsevents.backend.sportseventsbackend.util.EventTestUtil.getEventDto;

import com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem.CartItemRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart.UpdateShoppingCartRequestDto;
import com.sportsevents.backend.sportseventsbackend.cart.model.ShoppingCart;

public class ShoppingCartUtil {
    public static CartItemRequestDto getCartItemRequestDto() {
        CartItemRequestDto cartItemRequestDto = new CartItemRequestDto();
        cartItemRequestDto.setEventId(getEventDto().getId());
        cartItemRequestDto.setQuantity(1);
        return cartItemRequestDto;
    }

    public static UpdateShoppingCartRequestDto getUpdateShoppingCartRequestDto() {
        UpdateShoppingCartRequestDto updateShoppingCartRequestDto = new UpdateShoppingCartRequestDto();
        updateShoppingCartRequestDto.setQuantity(2);
        return updateShoppingCartRequestDto;
    }
}
