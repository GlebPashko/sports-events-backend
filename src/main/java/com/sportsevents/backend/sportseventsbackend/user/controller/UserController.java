package com.sportsevents.backend.sportseventsbackend.user.controller;

import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import com.sportsevents.backend.sportseventsbackend.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users management", description = "Endpoints for managing user")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user's profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public UserDto getUser() {
        return userService.getUser();
    }

    @Operation(summary = "Get user by id")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
