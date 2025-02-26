package com.sportsevents.backend.sportseventsbackend.event.dto.participant;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ParticipantDto {
    private Long id;
    private String userFirstName;
    private String userLastName;
    private String userId;
    private String userEmail;
    private Long eventId;
    private String eventTitle;
    private int quantity;
    private LocalDateTime registeredAt;
}

