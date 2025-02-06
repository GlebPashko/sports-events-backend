package com.sportsevents.backend.sportseventsbackend.cart.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateShoppingCartRequestDto {
    @Positive
    private int quantity;
}
