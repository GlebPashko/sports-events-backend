package com.sportsevents.backend.sportseventsbackend.event.mapper;

import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface EventMapper {
    Event toModel(CreateEventRequestDto eventDto);

    @Mapping(source = "author.id", target = "authorId")
    EventDto toDto(Event event);

    List<EventDto> toDtoList(List<Event> events);

    void updateEventFromDto(CreateEventRequestDto requestDto, @MappingTarget Event event);
}
