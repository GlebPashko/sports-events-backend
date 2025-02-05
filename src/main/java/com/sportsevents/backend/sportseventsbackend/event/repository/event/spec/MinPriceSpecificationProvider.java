package com.sportsevents.backend.sportseventsbackend.event.repository.event.spec;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MinPriceSpecificationProvider implements SpecificationProvider<Event> {
    private static final String MIN_PRICE_FIELD = "minPrice";

    @Override
    public String getKey() {
        return MIN_PRICE_FIELD;
    }

    @Override
    public Specification<Event> getSpecification(String[] params) {
        BigDecimal minPrice = new BigDecimal(params[0]);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }
}
