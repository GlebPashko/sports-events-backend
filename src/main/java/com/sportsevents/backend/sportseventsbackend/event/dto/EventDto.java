package com.sportsevents.backend.sportseventsbackend.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventDto {
    private Long id;
    private String title;
    private String descriptionSmall;
    private String descriptionFull;
    private String avatarImage;
    private String mainImage;
    private BigDecimal maximumParticipants;
    private LocalDateTime date;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private Long authorId;
    private LocalDateTime availableUntil;
}

