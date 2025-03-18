package com.sportsevents.backend.sportseventsbackend.controller;

import static com.sportsevents.backend.sportseventsbackend.util.UserUtil.getUserDto;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
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
public class UserControllerTest {
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
    @DisplayName("Verify getUser() method works")
    @Sql(scripts = "classpath:database/clear-basic-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void getUser_WithValidData_ShouldReturnUserDto() throws Exception {
        UserDto expected = getUserDto();

        mockMvc.perform(get("/user/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expected.getEmail()))
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expected.getLastName()))
                .andExpect(jsonPath("$.city").value(expected.getCity()))
                .andExpect(jsonPath("$.sex").value(expected.getSex()));
    }

    @Test
    @DisplayName("Verify getUserById() method works")
    @Sql(scripts = "classpath:database/clear-basic-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void getUserById_WithValidData_ShouldReturnUserDto() throws Exception {
        UserDto expected = getUserDto();

        mockMvc.perform(get("/user/{id}", CORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expected.getEmail()))
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expected.getLastName()))
                .andExpect(jsonPath("$.city").value(expected.getCity()))
                .andExpect(jsonPath("$.sex").value(expected.getSex()));
    }

    @Test
    @DisplayName("Verify getUserById() method works when id incorrect")
    @Sql(scripts = "classpath:database/clear-basic-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void getUserById_WithoutValidData_ShouldReturnException() throws Exception {
        Long userId = INCORRECT_ID;
        UserDto expected = getUserDto();
        String errorMessage = "User with id: " + userId + " not found";

        mockMvc.perform(get("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)));
    }
}
