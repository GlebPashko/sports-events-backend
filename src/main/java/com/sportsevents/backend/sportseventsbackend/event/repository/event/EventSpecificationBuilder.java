package com.sportsevents.backend.sportseventsbackend.event.repository.event;

import com.sportsevents.backend.sportseventsbackend.event.dto.EventSearchParameters;
import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationBuilder;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventSpecificationBuilder implements SpecificationBuilder<Event> {
    private static final String TITLE_FIELD = "title";
    private static final String MIN_PRICE_FIELD = "minPrice";
    private static final String MAX_PRICE_FIELD = "maxPrice";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";
    private static final String MIN_PARTICIPANTS_FIELD = "minParticipants";
    private static final String MAX_PARTICIPANTS_FIELD = "maxParticipants";
    private static final String CITY_FIELD = "city";
    private static final String ONLY_AVAILABLE_FIELD = "onlyAvailable";

    private final SpecificationProviderManager<Event> specificationProviderManager;

    @Override
    public Specification<Event> build(EventSearchParameters searchParameters) {
        Specification<Event> spec = Specification.where(null);

        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(TITLE_FIELD)
                    .getSpecification(searchParameters.title()));
        }

        if (searchParameters.minPrice() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(MIN_PRICE_FIELD)
                    .getSpecification(new String[]{searchParameters.minPrice().toString()}));
        }

        if (searchParameters.maxPrice() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(MAX_PRICE_FIELD)
                    .getSpecification(new String[]{searchParameters.maxPrice().toString()}));
        }

        if (searchParameters.startDate() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(START_DATE_FIELD)
                    .getSpecification(new String[]{searchParameters.startDate().toString()}));
        }

        if (searchParameters.endDate() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(END_DATE_FIELD)
                    .getSpecification(new String[]{searchParameters.endDate().toString()}));
        }

        if (searchParameters.minParticipants() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(MIN_PARTICIPANTS_FIELD)
                    .getSpecification(new String[]{searchParameters.minParticipants().toString()}));
        }

        if (searchParameters.maxParticipants() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(MAX_PARTICIPANTS_FIELD)
                    .getSpecification(new String[]{searchParameters.maxParticipants().toString()}));
        }

        if (searchParameters.city() != null && !searchParameters.city().isEmpty()) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(CITY_FIELD)
                    .getSpecification(new String[]{searchParameters.city().toString()}));
        }

        if (searchParameters.onlyAvailable() != null) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider(ONLY_AVAILABLE_FIELD)
                    .getSpecification(new String[]{searchParameters.onlyAvailable().toString()}));
        }

        return spec;
    }
}
