package com.sportsevents.backend.sportseventsbackend.authorization.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

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
