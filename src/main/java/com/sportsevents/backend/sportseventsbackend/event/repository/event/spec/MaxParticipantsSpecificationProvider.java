package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MaxParticipantsSpecificationProvider implements SpecificationProvider<Event> {
    private static final String MAX_PARTICIPANTS_FIELD = "maximumParticipants";

    @Override
    public String getKey() {
        return MAX_PARTICIPANTS_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        Integer maxParticipants = Integer.parseInt(params[0]);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("maximumParticipants"), maxParticipants);
    }
}
