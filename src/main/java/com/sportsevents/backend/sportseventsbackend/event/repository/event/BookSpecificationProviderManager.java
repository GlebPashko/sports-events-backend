package com.sportsevents.backend.sportseventsbackend.event.repository.event;

import com.sportsevents.backend.sportseventsbackend.event.model.Event;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProvider;
import com.sportsevents.backend.sportseventsbackend.event.repository.SpecificationProviderManager;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Event> {
    private final List<SpecificationProvider<Event>> eventSpecificationProvider;

    @Override
    public SpecificationProvider<Event> getSpecificationProvider(String key) {
        return eventSpecificationProvider
                .stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Don't find specification provider for " + key));
    }
}
