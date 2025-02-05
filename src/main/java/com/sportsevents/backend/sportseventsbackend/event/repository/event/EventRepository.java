package com.sportsevents.backend.sportseventsbackend.event.repository.event;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = "author")
    Page<Event> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "author")
    List<Event> findAll(Specification<Event> specification, Pageable pageable);
}
