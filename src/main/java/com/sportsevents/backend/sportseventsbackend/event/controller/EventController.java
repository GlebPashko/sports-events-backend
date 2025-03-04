package com.sportsevents.backend.sportseventsbackend.event.controller;

import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventPageableDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import com.sportsevents.backend.sportseventsbackend.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event management", description = "Endpoints for events")
@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Create a new event")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @PostMapping
    public EventDto createEvent(@RequestBody @Valid CreateEventRequestDto requestDto) {
        return eventService.saveEvent(requestDto);
    }

    @Operation(summary = "Find event by id")
    @GetMapping("/{id}")
    public EventDto findEventById(@PathVariable Long id) {
        return eventService.findEventById(id);
    }

    @Operation(summary = "Find all events")
    @GetMapping
    public EventPageableDto findAllEvents(Pageable pageable) {
        return eventService.findAllEvents(pageable);
    }

    @Operation(summary = "Find all events")
    @GetMapping("/latest")
    public List<EventDto> findLatestEvents() {
        return eventService.findLatestEvents();
    }

    @Operation(summary = "Search for event by parameters")
    @GetMapping("/search")
    public EventPageableDto searchEvents(EventSearchParameters searchParameters,
                                         Pageable pageable) {
        return eventService.searchEvents(searchParameters, pageable);
    }

    @Operation(summary = "Update a event by id")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @PutMapping("/{id}")
    public void updateEventById(@PathVariable Long id,
                                @RequestBody @Valid CreateEventRequestDto requestDto) {
        eventService.updateEventById(id, requestDto);
    }

    @Operation(summary = "Delete a event by id", description = "Mark the event "
            + "field 'is_deleted' = true")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable Long id) {
        eventService.deleteEventById(id);
    }
}
