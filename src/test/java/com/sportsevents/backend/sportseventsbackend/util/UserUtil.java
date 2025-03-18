package com.sportsevents.backend.sportseventsbackend.util;

import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserLoginRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserRegistrationRequestDto;
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

    public static UserRegistrationRequestDto getUserRegistrationRequestDto() {
        UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
        userRegistrationRequestDto.setEmail("user@example.com");
        userRegistrationRequestDto.setPassword("password_hash");
        userRegistrationRequestDto.setRepeatPassword("password_hash");
        userRegistrationRequestDto.setFirstName("Tony");
        userRegistrationRequestDto.setLastName("Soprano");
        userRegistrationRequestDto.setCity("ODESA");
        userRegistrationRequestDto.setSex("MALE");
        return userRegistrationRequestDto;
    }

    public static UserLoginRequestDto getUserLoginRequestDto() {
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail("user@example.com");
        userLoginRequestDto.setPassword("12345678");
        return userLoginRequestDto;
    }
}
