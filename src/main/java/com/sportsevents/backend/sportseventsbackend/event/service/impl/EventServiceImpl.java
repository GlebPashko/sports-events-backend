package com.sportsevents.backend.sportseventsbackend.event.service.impl;

import com.sportsevents.backend.sportseventsbackend.cart.repository.cartitem.CartItemRepository;
import com.sportsevents.backend.sportseventsbackend.cart.repository.orderitem.OrderItemRepository;
import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventPageableDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import com.sportsevents.backend.sportseventsbackend.event.mapper.EventMapper;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.event.EventRepository;
import com.sportsevents.backend.sportseventsbackend.event.repository.event.EventSpecificationBuilder;
import com.sportsevents.backend.sportseventsbackend.event.service.EventService;
import com.sportsevents.backend.sportseventsbackend.user.model.Role;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.repository.UserRepository;
import com.sportsevents.backend.sportseventsbackend.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final UserService userService;
    private final EventRepository eventRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final EventMapper eventMapper;
    private final EventSpecificationBuilder specificationBuilder;

    public EventDto saveEvent(CreateEventRequestDto requestDto) {
        User user = userService.getAuthenticatedUser();

        Event event = eventMapper.toModel(requestDto);
        event.setAuthor(user);
        eventRepository.save(event);
        EventDto eventDto = eventMapper.toDto(event);
        eventDto.setCategoryIds(requestDto.getCategoryIds());
        return eventDto;
    }

    public EventDto findEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Event with id: " + id + " not found"));
        EventDto eventDto = eventMapper.toDto(event);
        return eventDto;
    }

    public EventPageableDto findAllEvents(Pageable pageable) {
        Page<Event> page = eventRepository.findAll(pageable);

        return createPageableDto(page);
    }

    @Override
    public List<EventDto> findLatestEvents() {
        Pageable pageable = PageRequest.of(0, 4);

        return eventMapper.toDtoList(eventRepository.findAllByOrderByCreatedAtDesc(pageable));
    }

    public EventPageableDto searchEvents(EventSearchParameters searchParameters,
                                         Pageable pageable) {
        Specification<Event> specification = specificationBuilder.build(searchParameters);

        Page<Event> page = eventRepository.findAll(specification, pageable);

        return createPageableDto(page);
    }

    @Override
    public EventPageableDto findAllByCategoryId(Long id, Pageable pageable) {
        Page<Event> page = eventRepository.findAllByCategoryId(id, pageable);

        return createPageableDto(page);
    }

    @Override
    public EventPageableDto searchByCategoryId(Long id,
                                               EventSearchParameters searchParameters,
                                               Pageable pageable) {
        Specification<Event> specification = specificationBuilder.build(searchParameters);

        Specification<Event> categorySpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("categories").get("id"), id);

        Specification<Event> finalSpec = specification
                == null ? categorySpec : specification.and(categorySpec);

        Page<Event> page = eventRepository.findAll(finalSpec, pageable);

        return createPageableDto(page);
    }

    public void updateEventById(Long id, CreateEventRequestDto requestDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Event with id: " + id + " not found"));

        User currentUser = userService.getAuthenticatedUser();
        if (!event.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not the author of this event.");
        }

        eventMapper.updateEventFromDto(requestDto, event);
        eventRepository.save(event);
    }

    @Transactional
    public void deleteEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Event with id: " + id + " not found"));

        User currentUser = userService.getAuthenticatedUser();
        if (currentUser.getRoles().stream()
                .map(role -> role.getRole())
                .anyMatch(roleName -> roleName.equals(Role.RoleName.ROLE_ADMIN))
                || event.getAuthor().getId().equals(currentUser.getId())) {
            cartItemRepository.deleteByEvent(event);
            orderItemRepository.deleteByEvent(event);
            eventRepository.deleteById(event.getId());
        } else if (!event.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not the author of this event.");
        }
    }

    private EventPageableDto createPageableDto(Page page) {
        EventPageableDto eventPageableDto = new EventPageableDto();
        eventPageableDto.setEvents(eventMapper.toDtoList(page.getContent()));
        eventPageableDto.setThisPage(page.getNumber());
        eventPageableDto.setTotalPages(page.getTotalPages());
        eventPageableDto.setHasNextPage(page.hasNext());
        eventPageableDto.setHasPreviousPage(page.hasPrevious());
        eventPageableDto.setTotalElements(page.getTotalElements());

        return eventPageableDto;
    }
}

