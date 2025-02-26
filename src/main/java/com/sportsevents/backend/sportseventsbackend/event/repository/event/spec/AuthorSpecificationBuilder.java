package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationBuilder implements SpecificationProvider<Event> {
    private static final String AUTHOR_FIELD = "author";

    @Override
    public String getKey() {
        return AUTHOR_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        Long authorId = Long.valueOf(params[0]);
        User user = new User();
        user.setId(authorId);
        return (root, query, criteriaBuilder) -> root.get(AUTHOR_FIELD).in(user);
    }
}

