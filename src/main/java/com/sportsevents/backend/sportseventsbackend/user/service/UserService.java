package com.sportsevents.backend.sportseventsbackend.user.service;

import com.sportsevents.backend.sportseventsbackend.user.dto.AddRoleToUserRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.exception.RegistrationException;
import com.sportsevents.backend.sportseventsbackend.user.model.User;

public interface UserService {
    UserDto register(UserRegistrationRequestDto userDto) throws RegistrationException;

    UserDto getUser();

    UserDto getUserById(Long id);

    void addRoleToUser(AddRoleToUserRequestDto requestDto);

    User getAuthenticatedUser();
}
