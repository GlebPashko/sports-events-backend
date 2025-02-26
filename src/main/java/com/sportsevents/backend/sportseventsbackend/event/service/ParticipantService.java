package com.sportsevents.backend.sportseventsbackend.event.service;

import com.sportsevents.backend.sportseventsbackend.event.dto.participant.ParticipantDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import java.util.List;

public interface ParticipantService {
    List<ParticipantDto> findAllParticipantsForEvent(Long id);
}
