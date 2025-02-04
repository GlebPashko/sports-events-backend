package com.sportsevents.backend.sportseventsbackend.event.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateEventRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String descriptionSmall;
    @NotBlank
    private String descriptionFull;
    @NotBlank
    private String avatarImage;
    @NotBlank
    private String mainImage;
    @Positive
    private BigDecimal maximumParticipants;
    @Future
    @NotNull
    private LocalDateTime date;
    @PositiveOrZero
    private BigDecimal price;
    @Future
    @NotNull
    private LocalDateTime availableUntil;
}
