package com.sportsevents.backend.sportseventsbackend.cart.dto.cartitem;

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
