package com.sportsevents.backend.sportseventsbackend.event.repository.eventparticipant;

import com.sportsevents.backend.sportseventsbackend.event.model.EventParticipant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<EventParticipant, Long> {
    List<EventParticipant> findByEvent_Id(Long eventId);
}
