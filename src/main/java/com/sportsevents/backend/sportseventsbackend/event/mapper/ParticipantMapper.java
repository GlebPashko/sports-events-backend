package com.sportsevents.backend.sportseventsbackend.event.mapper;

import com.sportsevents.backend.sportseventsbackend.common.config.MapperConfig;
import com.sportsevents.backend.sportseventsbackend.event.dto.participant.ParticipantDto;
import com.sportsevents.backend.sportseventsbackend.event.model.EventParticipant;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ParticipantMapper {
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "event.title", target = "eventTitle")
    ParticipantDto toDto(EventParticipant participant);

    List<ParticipantDto> toDtoList(List<EventParticipant> participant);
}
