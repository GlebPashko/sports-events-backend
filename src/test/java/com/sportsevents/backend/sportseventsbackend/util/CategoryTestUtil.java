package com.sportsevents.backend.sportseventsbackend.util;

import com.sportsevents.backend.sportseventsbackend.event.dto.category.CategoryDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.category.CreateCategoryRequestDto;

public class CategoryTestUtil {
    private static final Long CORRECT_ID = 1L;

    public static CategoryDto getCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(CORRECT_ID);
        categoryDto.setName("Category one");
        categoryDto.setDescription("Category one description");

        return categoryDto;
    }

    public static CreateCategoryRequestDto getCreateCategoryRequestDto() {
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto();
        createCategoryRequestDto.setName("Category one");
        createCategoryRequestDto.setDescription("Category one description");
        return createCategoryRequestDto;
    }
}
