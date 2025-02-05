package com.sportsevents.backend.sportseventsbackend.event.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification<T> getSpecification(String[] params);
}
