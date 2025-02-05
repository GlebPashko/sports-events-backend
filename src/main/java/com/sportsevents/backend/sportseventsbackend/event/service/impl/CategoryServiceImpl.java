package com.sportsevents.backend.sportseventsbackend.event.service.impl;

import com.sportsevents.backend.sportseventsbackend.event.dto.category.CategoryDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.category.CreateCategoryRequestDto;
import com.sportsevents.backend.sportseventsbackend.event.mapper.CategoryMapper;
import com.sportsevents.backend.sportseventsbackend.event.model.Category;
import com.sportsevents.backend.sportseventsbackend.event.repository.category.CategoryRepository;
import com.sportsevents.backend.sportseventsbackend.event.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.save(categoryMapper.toEntity(requestDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(
                "Category with id " + id + " not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(
                "Category with id " + id + " not found"));
        categoryMapper.updateCategoryFromDto(requestDto, category);
        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
