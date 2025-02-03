package com.sportsevents.backend.sportseventsbackend.authorization.controller;

import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserLoginRequestDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserLoginResponseDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.authorization.exception.RegistrationException;
import com.sportsevents.backend.sportseventsbackend.authorization.service.UserService;
import com.sportsevents.backend.sportseventsbackend.common.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management", description = "Endpoints for authentication users")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user")
    @PostMapping("/registration")
    public UserDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @Operation(summary = "User authentication")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
