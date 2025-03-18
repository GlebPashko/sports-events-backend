package com.sportsevents.backend.sportseventsbackend.util;

import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventPageableDto;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventTestUtil {
    private static final Long CORRECT_ID = 1L;

    public static Event getEvent() {
        Long eventId = CORRECT_ID;
        Event event = new Event();

        event.setId(eventId);
        event.setTitle("Sample Event");
        event.setDescriptionSmall("Short description");
        event.setDescriptionFull("Full description of the event.");
        event.setAvatarImage("avatar.jpg");
        event.setMainImage("main_image.jpg");
        event.setVideoLink("https://example.com/video");
        event.setMaximumParticipants(new BigDecimal("100"));
        event.setDateOfStartEvent(LocalDateTime.now().plusDays(10));
        event.setPrice(new BigDecimal("50.00"));
        event.setCity("Odesa");
        event.setGoogleMapCoordinates("0.0,0.0");
        event.setRegistrationAvailableUntil(LocalDateTime.now().plusDays(5));

        return event;
    }

    public static EventDto getEventDto() {
        EventDto eventDto = new EventDto();

        eventDto.setId(CORRECT_ID);
        eventDto.setTitle("Sample Event");
        eventDto.setDescriptionSmall("Short description.");
        eventDto.setDescriptionFull("Full description of the event.");
        eventDto.setAvatarImage("avatar.jpg");
        eventDto.setMainImage("main_image.jpg");
        eventDto.setVideoLink("https://example.com/video");
        eventDto.setMaximumParticipants(BigDecimal.TEN);
        eventDto.setDateOfStartEvent(LocalDateTime.of(2026, 4, 1, 0, 0));
        eventDto.setPrice(BigDecimal.TEN);
        eventDto.setCreatedAt(LocalDateTime.of(2026, 2, 1, 0, 0));
        eventDto.setCity("Odesa");
        eventDto.setGoogleMapCoordinates("0.0,0.0");

        eventDto.setAuthorId(2L);

        eventDto.setRegistrationAvailableUntil(LocalDateTime.of(2026, 3, 1, 0, 0));

        Set<Long> categoryIds = new HashSet<>();
        categoryIds.add(1L);
        eventDto.setCategoryIds(categoryIds);

        return eventDto;
    }

    public static EventPageableDto getEventPageableDto() {
        EventPageableDto eventPageableDto = new EventPageableDto();
        eventPageableDto.setThisPage(0);
        eventPageableDto.setTotalPages(1);
        eventPageableDto.setHasNextPage(false);
        eventPageableDto.setHasPreviousPage(false);
        eventPageableDto.setTotalElements(1);
        eventPageableDto.setEvents(List.of(getEventDto()));

        return eventPageableDto;
    }

    public static CreateEventRequestDto getEventRequestDto() {
        CreateEventRequestDto eventRequestDto = new CreateEventRequestDto();

        eventRequestDto.setTitle("Sample Event");
        eventRequestDto.setDescriptionSmall("Short description.");
        eventRequestDto.setDescriptionFull("Full description of the event.");
        eventRequestDto.setAvatarImage("avatar.jpg");
        eventRequestDto.setMainImage("main_image.jpg");
        eventRequestDto.setVideoLink("https://example.com/video");
        eventRequestDto.setMaximumParticipants(BigDecimal.TEN);
        eventRequestDto.setDateOfStartEvent(LocalDateTime.of(2026, 4, 1, 0, 0));
        eventRequestDto.setPrice(BigDecimal.TEN);
        eventRequestDto.setCity("Odesa");
        eventRequestDto.setGoogleMapCoordinates("0.0,0.0");

        eventRequestDto.setRegistrationAvailableUntil(LocalDateTime.of(2026, 3, 1, 0, 0));

        Set<Long> categoryIds = new HashSet<>();
        categoryIds.add(1L);
        eventRequestDto.setCategoryIds(categoryIds);

        return eventRequestDto;
    }
}
