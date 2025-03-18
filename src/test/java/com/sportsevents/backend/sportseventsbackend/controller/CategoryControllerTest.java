package com.sportsevents.backend.sportseventsbackend.controller;

import static com.sportsevents.backend.sportseventsbackend.util.CategoryTestUtil.getCategoryDto;
import static com.sportsevents.backend.sportseventsbackend.util.CategoryTestUtil.getCreateCategoryRequestDto;
import static com.sportsevents.backend.sportseventsbackend.util.EventTestUtil.getEventPageableDto;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventPageableDto;
import com.sportsevents.backend.sportseventsbackend.event.dto.category.CategoryDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/clear-full-db.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/user/add-roles-to-roles-table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/user/add-users-with-role-to-users-table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CategoryControllerTest {
    private static final Long CORRECT_ID = 1L;
    private static final Long INCORRECT_ID = 100L;

    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Verify save() method works")
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    @Transactional
    public void saveCategory_ValidCreateCategoryRequestDto_ShouldReturnCategoryDto() throws Exception {
        CategoryDto expected = getCategoryDto();
        String jsonRequest = objectMapper.writeValueAsString(getCreateCategoryRequestDto());

        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
    }

    @Test
    @DisplayName("save() should return FORBIDDEN if user lacks valid role")
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void saveCategory_WithoutValidRole_ShouldReturnForbiddenStatus() throws Exception {
        CategoryDto expected = getCategoryDto();
        String jsonRequest = objectMapper.writeValueAsString(getCreateCategoryRequestDto());

        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Verify findAll() method works")
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Sql(scripts = "classpath:database/category/add-category-to-categories-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/delete-category-from-categories-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void findAll_WithData_ShouldCategoryDtos() throws Exception {
        CategoryDto expected = getCategoryDto();

        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].description", is(expected.getDescription())));
    }

    @Test
    @DisplayName("Verify findById() method works")
    @Sql(scripts = "classpath:database/category/add-category-to-categories-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/delete-category-from-categories-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void findById_WithValidData_ShouldCategoryDto() throws Exception {
        CategoryDto expected = getCategoryDto();

        mockMvc.perform(get("/categories/{id}", CORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
    }

    @Test
    @DisplayName("Verify findById() method returns an exception when category by id not exists")
    @Sql(scripts = "classpath:database/category/add-category-to-categories-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/delete-category-from-categories-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void findById_WithoutValidData_ShouldReturnException() throws Exception {
        String errorMessage = "Category with id: " + INCORRECT_ID + " not found";

        mockMvc.perform(get("/categories/{id}", INCORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    @DisplayName("Verify updateCategory() method works")
    @Sql(scripts = "classpath:database/category/add-category-to-categories-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/delete-category-from-categories-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN", "ORGANIZER", "USER"})
    @Transactional
    public void updateCategoryById_WithValidData_ShouldCategoryDto() throws Exception {
        CategoryDto expected = getCategoryDto();
        String jsonRequest = objectMapper.writeValueAsString(getCreateCategoryRequestDto());

        mockMvc.perform(put("/categories/{id}", CORRECT_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
    }

    @Test
    @DisplayName("Verify deleteCategory() method works")
    @Sql(scripts = "classpath:database/category/add-category-to-categories-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/category/delete-category-from-categories-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN", "ORGANIZER", "USER"})
    @Transactional
    public void deleteCategory_WithValidId_ShouldDeleteCategory() throws Exception {
        Long categoryId = CORRECT_ID;

        mockMvc.perform(delete("/categories/{id}", categoryId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Verify getEventsByCategoryId() method works")
    @Sql(scripts = "classpath:database/event/create-event-with-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/event/delete-event-from-events-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void getEventsByCategoryId_WithValidData_ShouldEventPageableDto() throws Exception {
        EventPageableDto expected = getEventPageableDto();

        mockMvc.perform(get("/categories/{id}/events", CORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events.size()").value(expected.getEvents().size()))
                .andExpect(jsonPath("$.thisPage").value(expected.getThisPage()))
                .andExpect(jsonPath("$.totalPages").value(expected.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(expected.getTotalElements()));
    }

    @Test
    @DisplayName("Verify searchEventByCategoryId() method works")
    @Sql(scripts = "classpath:database/event/create-event-with-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/event/delete-event-from-events-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void searchEventsByCategoryId_WithValidData_ShouldEventPageableDto() throws Exception {
        mockMvc.perform(get("/categories/{id}/events/search", CORRECT_ID)
                        .param("title", "Sample Event")
                        .param("minPrice", "9")
                        .param("maxPrice", "100")
                        .param("startDate", "2024-03-10T10:00:00")
                        .param("endDate", "2028-03-15T18:00:00")
                        .param("city", "Odesa")
                        .param("author", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events.size()").value(1))
                .andExpect(jsonPath("$.thisPage").value(0))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
