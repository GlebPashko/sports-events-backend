package com.sportsevents.backend.sportseventsbackend.util;

import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import java.time.LocalDateTime;

public class UserUtil {
    public static UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail("user@example.com");
        userDto.setFirstName("Tony");
        userDto.setLastName("Soprano");
        userDto.setCity("ODESA");
        userDto.setSex("MALE");
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setBlocked(false);
        return userDto;
    }
}
