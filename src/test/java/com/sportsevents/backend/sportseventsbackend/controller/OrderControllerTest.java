package com.sportsevents.backend.sportseventsbackend.controller;

import static com.sportsevents.backend.sportseventsbackend.util.EventTestUtil.getEventRequestDto;
import static com.sportsevents.backend.sportseventsbackend.util.OrderTestUtil.getCreateOrderRequestDto;
import static com.sportsevents.backend.sportseventsbackend.util.OrderTestUtil.getOrderResponseDto;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsevents.backend.sportseventsbackend.cart.dto.order.OrderResponseDto;
import com.sportsevents.backend.sportseventsbackend.util.OrderTestUtil;
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
public class OrderControllerTest {
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
    @DisplayName("Verify createOrder() method works")
    @Sql(scripts = "classpath:database/event/add-event-to-events-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/cart/add-shopping-cart-items.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void createOrder_ValidData_ShouldReturnOrderDto() throws Exception {
        OrderResponseDto expected = getOrderResponseDto();

        String jsonRequest = objectMapper.writeValueAsString(getCreateOrderRequestDto());

        mockMvc.perform(post("/orders")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.userId").value(expected.getUserId()))
                .andExpect(jsonPath("$.orderItems.size()").value(1))
                .andExpect(jsonPath("$.total").value(expected.getTotal().doubleValue()))
                .andExpect(jsonPath("$.status").value(expected.getStatus().name()));
    }

    @Test
    @DisplayName("Verify getOrders() method works")
    @Sql(scripts = "classpath:database/event/add-event-to-events-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/cart/add-shopping-cart-items.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/clear-basic-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user@example.com", roles = "USER")
    @Transactional
    public void getOrders_ValidData_ShouldReturnOrderDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(getCreateOrderRequestDto());

        mockMvc.perform(get("/orders")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
