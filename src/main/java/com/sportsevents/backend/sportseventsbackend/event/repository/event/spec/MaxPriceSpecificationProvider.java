package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MaxPriceSpecificationProvider implements SpecificationProvider<Event> {
    private static final String MAX_PRICE_FIELD = "maxPrice";

    @Override
    public String getKey() {
        return MAX_PRICE_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        BigDecimal maxPrice = new BigDecimal(params[0]);
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
