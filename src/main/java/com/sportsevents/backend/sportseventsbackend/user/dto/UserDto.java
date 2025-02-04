package com.sportsevents.backend.sportseventsbackend.user.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String sex;
    private LocalDateTime createdAt;
    private boolean isBlocked;
}
