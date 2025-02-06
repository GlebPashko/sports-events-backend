package com.sportsevents.backend.sportseventsbackend.shopping_cart.dto.order;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long eventId;
    private String eventTitle;
    private String descriptionSmall;
    private String avatarImage;
    private String price;
    private String city;
    private LocalDateTime dateOfStartEvent;
    private int quantity;
}
