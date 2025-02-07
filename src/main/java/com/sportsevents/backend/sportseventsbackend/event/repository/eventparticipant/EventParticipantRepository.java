package com.sportsevents.backend.sportseventsbackend.event.repository.eventparticipant;

import com.sportsevents.backend.sportseventsbackend.event.model.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {

}
