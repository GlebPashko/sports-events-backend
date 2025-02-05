package com.sportsevents.backend.sportseventsbackend.event.service;

import com.sportsevents.backend.sportseventsbackend.event.dto.category.CategoryDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.category.CreateCategoryRequestDto;
import java.util.List;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto getById(Long id);

    List<CategoryDto> findAll();

    CategoryDto update(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);
}
