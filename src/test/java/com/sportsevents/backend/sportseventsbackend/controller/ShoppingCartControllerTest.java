package com.sportsevents.backend.sportseventsbackend.controller;

import static com.sportsevents.backend.sportseventsbackend.util.ShoppingCartUtil.getCartItemRequestDto;
import static com.sportsevents.backend.sportseventsbackend.util.ShoppingCartUtil.getUpdateShoppingCartRequestDto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ShoppingCartControllerTest {
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
    @DisplayName("Verify getShoppingCart() method works")
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void getShoppingCart_ValidData_ShouldReturnShoppingCartResponseDto() throws Exception {
        mockMvc.perform(get("/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Verify addEventToShoppingCart() method works")
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Sql(scripts = "classpath:database/event/add-event-to-events-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void addEventToShoppingCart_WithValidData_ShouldReturnShoppingCartResponseDto()
            throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(getCartItemRequestDto());

        mockMvc.perform(post("/cart")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    @DisplayName("Verify updateQuantity() method works")
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Sql(scripts = "classpath:database/event/add-event-to-events-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void updateQuantity_WithValidData_ShouldReturnShoppingCartResponseDto()
            throws Exception {
        String jsonRequestGetCartItem = objectMapper.writeValueAsString(getCartItemRequestDto());

        mockMvc.perform(post("/cart")
                        .content(jsonRequestGetCartItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));

        String jsonRequestUpdateCartRequest = objectMapper
                .writeValueAsString(getUpdateShoppingCartRequestDto());

        mockMvc.perform(put("/cart/{id}", CORRECT_ID)
                        .content(jsonRequestUpdateCartRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.cartItems[0].quantity")
                        .value(getUpdateShoppingCartRequestDto().getQuantity()));
    }

    @Test
    @DisplayName("Verify deleteEventFromCart() method works")
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Sql(scripts = "classpath:database/event/add-event-to-events-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Transactional
    public void deleteEventFromCart_WithValidData_ShouldReturnOkStatus()
            throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(getCartItemRequestDto());

        mockMvc.perform(post("/cart")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));

        mockMvc.perform(delete("/cart/{id}", CORRECT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
