package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Event> {
    private static final String TITLE_FIELD = "title";

    @Override
    public String getKey() {
        return TITLE_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = Arrays.stream(params)
                    .map(param -> criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(TITLE_FIELD)),
                            "%" + param.toLowerCase() + "%"
                    ))
                    .toArray(Predicate[]::new);
            return criteriaBuilder.or(predicates);
        };
    }
}