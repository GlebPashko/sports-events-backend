package com.sportsevents.backend.sportseventsbackend.event.repository.event;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(attributePaths = "author")
    Page<Event> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "author")
    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = "author")
    Page<Event> findAll(Specification<Event> specification, Pageable pageable);

    @Query("SELECT e from Event e JOIN e.categories ec where ec.id = :categoryId")
    Page<Event> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}
