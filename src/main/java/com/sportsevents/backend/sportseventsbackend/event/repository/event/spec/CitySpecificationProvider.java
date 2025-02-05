package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CitySpecificationProvider implements SpecificationProvider<Event> {
    private static final String CITY_FIELD = "city";

    @Override
    public String getKey() {
        return CITY_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(CITY_FIELD).in((Object[]) params);
    }
}
