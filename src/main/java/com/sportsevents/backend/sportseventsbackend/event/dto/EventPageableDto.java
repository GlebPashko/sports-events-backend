package com.sportsevents.backend.sportseventsbackend.event.dto;

import java.util.List;
import lombok.Data;

@Data
public class EventPageableDto {
    private int thisPage;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private long totalElements;
    private List<EventDto> events;
}
