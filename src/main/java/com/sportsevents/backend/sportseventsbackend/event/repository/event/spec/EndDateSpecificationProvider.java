package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EndDateSpecificationProvider implements SpecificationProvider<Event> {
    private static final String END_DATE_FIELD = "endDate";

    @Override
    public String getKey() {
        return END_DATE_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        LocalDateTime endDate = LocalDateTime.parse(params[0]);
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateOfStartEvent"), endDate);
    }
}
