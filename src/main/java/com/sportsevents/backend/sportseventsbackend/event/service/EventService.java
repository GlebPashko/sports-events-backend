package com.sportsevents.backend.sportseventsbackend.event.service;

import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventPageableDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface EventService {
    EventDto saveEvent(CreateEventRequestDto requestDto);

    EventDto findEventById(Long id);

    EventPageableDto findAllEvents(Pageable pageable);

    List<EventDto> searchEvents(EventSearchParameters searchParameters, Pageable pageable);

    void updateEventById(Long id, CreateEventRequestDto requestDto);

    void deleteEventById(Long id);
}

