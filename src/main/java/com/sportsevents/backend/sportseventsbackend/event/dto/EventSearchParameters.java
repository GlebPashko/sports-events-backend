package com.sportsevents.backend.sportseventsbackend.event.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventSearchParameters(
        String[] title,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal minParticipants,
        BigDecimal maxParticipants,
        String city,
        Long author,
        Boolean onlyAvailable
) {}
