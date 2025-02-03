package com.sportsevents.backend.sportseventsbackend.authorization.service;

import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.authorization.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto userDto) throws RegistrationException;
}
