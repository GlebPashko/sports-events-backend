package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StartDateSpecificationProvider implements SpecificationProvider<Event> {
    private static final String START_DATE_FIELD = "startDate";

    @Override
    public String getKey() {
        return START_DATE_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        LocalDateTime startDate = LocalDateTime.parse(params[0]);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfStartEvent"), startDate);
    }
}
