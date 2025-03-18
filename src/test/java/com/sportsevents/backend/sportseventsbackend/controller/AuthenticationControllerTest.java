package com.sportsevents.backend.sportseventsbackend.controller;

import static com.sportsevents.backend.sportseventsbackend.util.UserUtil.getUserDto;
import static com.sportsevents.backend.sportseventsbackend.util.UserUtil.getUserLoginRequestDto;
import static com.sportsevents.backend.sportseventsbackend.util.UserUtil.getUserRegistrationRequestDto;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserLoginRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/clear-full-db.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/user/add-roles-to-roles-table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AuthenticationControllerTest {
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
    @DisplayName("Verify register() method works")
    @Sql(scripts = "classpath:database/clear-basic-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/delete-users-from-users-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void registerUser_WithValidData_ShouldReturnUserDto() throws Exception {
        UserDto expected = getUserDto();
        String jsonRequest = objectMapper.writeValueAsString(getUserRegistrationRequestDto());

        mockMvc.perform(post("/auth/registration")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expected.getEmail()))
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expected.getLastName()))
                .andExpect(jsonPath("$.city").value(expected.getCity()))
                .andExpect(jsonPath("$.sex").value(expected.getSex()));
    }

    @Test
    @DisplayName("Verify register() method throws an exception if the data is incorrect")
    @Sql(scripts = "classpath:database/clear-basic-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/delete-users-from-users-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void registerUser_WithoutValidData_ShouldReturnException() throws Exception {
        String errorMessage = "Invalid email format";

        UserLoginRequestDto userLoginRequestDto = getUserLoginRequestDto();
        userLoginRequestDto.setEmail("wrongemail");
        String jsonRequest = objectMapper.writeValueAsString(userLoginRequestDto);

        mockMvc.perform(post("/auth/registration")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    @DisplayName("Verify login() method works")
    @Sql(scripts = "classpath:database/user/add-user-to-users-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/delete-users-from-users-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void loginUser_WithValidData_ShouldReturnUserLoginResponseDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(getUserLoginRequestDto());

        mockMvc.perform(post("/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    @DisplayName("Verify login() method throws an exception if the data is incorrect")
    @Sql(scripts = "classpath:database/user/add-user-to-users-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/delete-users-from-users-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void loginUser_WithoutValidData_ShouldReturnException() throws Exception {
        String errorMessage = "Invalid username or password";

        UserLoginRequestDto userLoginRequestDto = getUserLoginRequestDto();
        userLoginRequestDto.setPassword("wrongPassword");
        String jsonRequest = objectMapper.writeValueAsString(userLoginRequestDto);

        mockMvc.perform(post("/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(errorMessage)));
    }
}
