package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OnlyAvailableSpecificationProvider implements SpecificationProvider<Event> {
    private static final String ONLY_AVAILABLE_FIELD = "onlyAvailable";

    @Override
    public String getKey() {
        return ONLY_AVAILABLE_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        Boolean onlyAvailable = Boolean.parseBoolean(params[0]);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ONLY_AVAILABLE_FIELD), onlyAvailable);
    }
}
