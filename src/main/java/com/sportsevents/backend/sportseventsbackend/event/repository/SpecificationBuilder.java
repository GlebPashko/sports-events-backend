package com.sportsevents.backend.sportseventsbackend.event.repository;

import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(EventSearchParameters searchParameters);
}
