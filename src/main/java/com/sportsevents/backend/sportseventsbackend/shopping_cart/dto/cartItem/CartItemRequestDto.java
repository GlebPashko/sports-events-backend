package com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.cartItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotNull
    @Positive
    private Long eventId;
    @Positive
    private int quantity;
}
