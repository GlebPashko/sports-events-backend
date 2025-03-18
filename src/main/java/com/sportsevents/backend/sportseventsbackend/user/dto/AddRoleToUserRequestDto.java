package com.sportsevents.backend.sportseventsbackend.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddRoleToUserRequestDto {
    @NotNull
    Long userId;
    @NotBlank
    String role;
}
