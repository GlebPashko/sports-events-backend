package com.sportsevents.backend.sportseventsbackend.event.controller;

import com.sportsevents.backend.sportseventsbackend.event.dto.CreateEventRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.participant.ParticipantDto;
import com.sportsevents.backend.sportseventsbackend.event.service.ParticipantService;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Participant management", description = "Endpoints for participant")
@RequiredArgsConstructor
@RestController
@RequestMapping("/participant")
public class ParticipantController {
    private final ParticipantService participantService;

    @Operation(summary = "Create a new event")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    @GetMapping("/event/{id}")
    public List<ParticipantDto> findAllParticipantsForEvent(@PathVariable Long id) {
        return participantService.findAllParticipantsForEvent(id);
    }
}
