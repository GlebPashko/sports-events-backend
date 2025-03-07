package com.sportsevents.backend.sportseventsbackend.controller;

import static com.sportsevents.backend.sportseventsbackend.util.TestUtil.getEventDto;
import static com.sportsevents.backend.sportseventsbackend.util.TestUtil.getEventRequestDto;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsevents.backend.sportseventsbackend.event.dto.EventDto;
import com.sportsevents.backend.sportseventsbackend.event.service.EventService;
import jakarta.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/add-organizer-to-users-table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "classpath:database/add-admin-to-users-table.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EventControllerTest {
    private static final Long CORRECT_ID = 1L;
    private static final Long INCORRECT_ID = 100L;

    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private EventService eventService;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Verify save() method works")
    @WithMockUser(username = "organizer@example.com", roles = "ORGANIZER")
    @Transactional
    public void saveEvent_ValidCreateEventRequestDto_ShouldReturnEventDto() throws Exception {
        EventDto expected = getEventDto();
        String jsonRequest = objectMapper.writeValueAsString(getEventRequestDto());

        mockMvc.perform(post("/events")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expected.getPrice()))
                .andExpect(jsonPath("$.title", is(expected.getTitle())))
                .andExpect(jsonPath("$.avatarImage", is(expected.getAvatarImage())))
                .andExpect(jsonPath("$.descriptionSmall", is(expected.getDescriptionSmall())))
                .andExpect(jsonPath("$.descriptionFull", is(expected.getDescriptionFull())))
                .andExpect(jsonPath("$.maximumParticipants").value(expected.getMaximumParticipants()))
                .andExpect(jsonPath("$.dateOfStartEvent").value(expected.getDateOfStartEvent().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
                .andExpect(jsonPath("$.city", is(expected.getCity())))
                .andExpect(jsonPath("$.google_map_coordinates", is(expected.getGoogle_map_coordinates())))
                .andExpect(jsonPath("$.registrationAvailableUntil").value(expected.getRegistrationAvailableUntil().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
    }

    @Test
    @Sql(scripts = "classpath:database/add-event-to-events-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-event-from-events-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Verify findAll() method works with real DB")
    @Transactional
    public void getAll_ValidData_ShouldReturnEventPageableDto() throws Exception {
        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events.size()").value(1))
                .andExpect(jsonPath("$.thisPage").value(0))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @Sql(scripts = "classpath:database/add-event-to-events-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-event-from-events-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Verify findById() method works")
    @Transactional
    public void getEvenById_ValidData_ShouldReturnEventDto() throws Exception {
        long eventId = CORRECT_ID;
        EventDto expected = getEventDto();

        mockMvc.perform(get("/events/" + eventId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expected.getPrice().doubleValue()))
                .andExpect(jsonPath("$.title", is(expected.getTitle())))
                .andExpect(jsonPath("$.avatarImage", is(expected.getAvatarImage())))
                .andExpect(jsonPath("$.descriptionSmall", is(expected.getDescriptionSmall())))
                .andExpect(jsonPath("$.descriptionFull", is(expected.getDescriptionFull())))
                .andExpect(jsonPath("$.maximumParticipants").value(expected.getMaximumParticipants().doubleValue()))
                .andExpect(jsonPath("$.dateOfStartEvent").value(expected.getDateOfStartEvent().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))))
                .andExpect(jsonPath("$.city", is(expected.getCity())))
                .andExpect(jsonPath("$.google_map_coordinates", is(expected.getGoogle_map_coordinates())))
                .andExpect(jsonPath("$.registrationAvailableUntil").value(expected.getRegistrationAvailableUntil().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
    }

    @Test
    @DisplayName("Verify findById() method works when book by id not exists")
    @Transactional
    public void getEventById_NotValidId_ShouldReturnException() throws Exception {
        long eventId = INCORRECT_ID;
        String errorMessage = "Event with id: " + eventId + " not found";

        mockMvc.perform(get("/events/" + eventId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    @Sql(scripts = "classpath:database/add-event-to-events-table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/delete-event-from-events-table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Verify deleteById() method works")
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN", "ORGANIZER", "USER"})
    @Transactional
    public void deleteEventById_ValidId_Success() throws Exception {
        long eventId = CORRECT_ID;

        mockMvc.perform(delete("/events/" + eventId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/events/" + eventId))
                .andExpect(status().isNotFound());
    }
}
