package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MinParticipantsSpecificationProvider implements SpecificationProvider<Event> {
    private static final String MIN_PARTICIPANTS_FIELD = "minimumParticipants";

    @Override
    public String getKey() {
        return MIN_PARTICIPANTS_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        Integer minParticipants = Integer.parseInt(params[0]);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("maximumParticipants"), minParticipants);
    }
}
