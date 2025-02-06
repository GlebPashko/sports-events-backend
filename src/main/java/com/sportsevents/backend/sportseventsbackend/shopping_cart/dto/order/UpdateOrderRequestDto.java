package com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderRequestDto {
    @NotBlank
    private String status;
}
