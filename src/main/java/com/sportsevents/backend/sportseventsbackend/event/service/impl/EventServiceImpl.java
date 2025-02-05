package com.sportsevents.backend.sportseventsbackend.event.service.impl;

import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventPageableDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import com.sportsevents.backend.sportseventsbackend.event.mapper.EventMapper;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.event.EventRepository;
import com.sportsevents.backend.sportseventsbackend.event.repository.event.EventSpecificationBuilder;
import com.sportsevents.backend.sportseventsbackend.event.service.EventService;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final EventSpecificationBuilder specificationBuilder;

    public EventDto saveEvent(CreateEventRequestDto requestDto) {
        User user = getAuthenticatedUser();

        Event event = eventMapper.toModel(requestDto);
        event.setAuthor(user);
        eventRepository.save(event);
        EventDto eventDto = eventMapper.toDto(event);
        return eventDto;
    }

    public EventDto findEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        EventDto eventDto = eventMapper.toDto(event);
        return eventDto;
    }

    public EventPageableDto findAllEvents(Pageable pageable) {
        Page<Event> page = eventRepository.findAll(pageable);

        EventPageableDto eventPageableDto = new EventPageableDto();
        eventPageableDto.setEvents(eventMapper.toDtoList(page.getContent()));
        eventPageableDto.setThisPage(page.getNumber());
        eventPageableDto.setTotalPages(page.getTotalPages());
        eventPageableDto.setHasNextPage(page.hasNext());
        eventPageableDto.setHasPreviousPage(page.hasPrevious());
        eventPageableDto.setTotalElements(page.getTotalElements());

        return eventPageableDto;
    }

    public List<EventDto> searchEvents(EventSearchParameters searchParameters, Pageable pageable) {
        Specification<Event> specification = specificationBuilder.build(searchParameters);
        return eventMapper.toDtoList(eventRepository.findAll(specification, pageable));
    }

    public void updateEventById(Long id, CreateEventRequestDto requestDto) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Event with id: " + id + " not found"));
        eventMapper.updateEventFromDto(requestDto, event);
        eventRepository.save(event);
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    private User getAuthenticatedUser() {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal().toString();
        return userRepository.findByEmail(userEmail).orElseThrow(); // TODO: Need to throw exception
    }
}

