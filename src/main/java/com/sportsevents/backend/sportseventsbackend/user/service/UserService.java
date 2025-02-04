package com.sportsevents.backend.sportseventsbackend.user.service;

import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto userDto) throws RegistrationException;
}
