package com.sportsevents.backend.sportseventsbackend.event.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
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
    @NotBlank
    private String videoLink;
    @Positive
    private BigDecimal maximumParticipants;
    @Future
    @NotNull
    private LocalDateTime dateOfStartEvent;
    @PositiveOrZero
    private BigDecimal price;
    @NotBlank
    private String city;
    @NotBlank
    private String google_map_coordinates;
    @Future
    @NotNull
    private LocalDateTime registrationAvailableUntil;
    private Set<Long> categoryIds;
}
