package com.sportsevents.backend.sportseventsbackend.event.service.impl;

import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import com.sportsevents.backend.sportseventsbackend.event.mapper.EventMapper;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.EventRepository;
import com.sportsevents.backend.sportseventsbackend.event.service.EventService;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public EventDto saveEvent(CreateEventRequestDto requestDto) {
        User user = getAuthenticatedUser();

        Event event = eventMapper.toModel(requestDto);
        event.setAuthor(user);
        eventRepository.save(event);
        EventDto eventDto = eventMapper.toDto(event);
        return eventDto;
    }

    public EventDto findEventById(Long id) {
        // TODO: Реалізувати пошук події за ID
        return null;
    }

    public List<EventDto> findAllEvents(Pageable pageable) {
        // TODO: Реалізувати отримання всіх подій
        return null;
    }

    public void updateEventById(Long id, CreateEventRequestDto requestDto) {
        // TODO: Реалізувати оновлення події за ID
    }

    public List<EventDto> searchEvents(EventSearchParameters searchParameters, Pageable pageable) {
        // TODO: Реалізувати пошук подій за параметрами
        return null;
    }

    public void deleteEventById(Long id) {
        // TODO: Реалізувати видалення події (помітити is_deleted = true)
    }

    private User getAuthenticatedUser() {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal().toString();
        return userRepository.findByEmail(userEmail).orElseThrow(); // TODO: Need to throw exception
    }
}

