package com.sportsevents.backend.sportseventsbackend.event.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);
}
