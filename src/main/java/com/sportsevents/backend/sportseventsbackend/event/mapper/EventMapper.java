package com.sportsevents.backend.sportseventsbackend.event.mapper;

import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface EventMapper {
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoryById")
    Event toModel(CreateEventRequestDto eventDto);

    @Mapping(source = "author.id", target = "authorId")
    EventDto toDto(Event event);

    List<EventDto> toDtoList(List<Event> events);

    List<EventDto> toDtoList(Page<Event> events);

    void updateEventFromDto(CreateEventRequestDto requestDto, @MappingTarget Event event);

    @Named("eventFromId")
    default Event eventFromId(Long id) {
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
