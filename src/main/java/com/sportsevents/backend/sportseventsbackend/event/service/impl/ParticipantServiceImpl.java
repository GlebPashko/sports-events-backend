package com.sportsevents.backend.sportseventsbackend.event.service.impl;

import com.sportsevents.backend.sportseventsbackend.event.dto.participant.ParticipantDto;
import com.sportsevents.backend.sportseventsbackend.event.mapper.ParticipantMapper;
import com.sportsevents.backend.sportseventsbackend.event.model.EventParticipant;
import com.sportsevents.backend.sportseventsbackend.event.repository.eventparticipant.ParticipantRepository;
import com.sportsevents.backend.sportseventsbackend.event.service.ParticipantService;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Override
    public List<ParticipantDto> findAllParticipantsForEvent(Long id) {
        List<EventParticipant> eventParticipants = participantRepository.findByEvent_Id(id);
        return participantMapper.toDtoList(eventParticipants);
    }
}
