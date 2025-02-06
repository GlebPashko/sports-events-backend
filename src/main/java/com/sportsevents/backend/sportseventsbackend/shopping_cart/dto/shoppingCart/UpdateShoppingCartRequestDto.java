package com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.shoppingCart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateShoppingCartRequestDto {
    @Positive
    private int quantity;
}
