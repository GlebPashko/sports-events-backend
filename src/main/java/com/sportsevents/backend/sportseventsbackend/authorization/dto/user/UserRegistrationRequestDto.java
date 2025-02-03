package com.sportsevents.backend.sportseventsbackend.authorization.dto.user;

import com.sportsevents.backend.sportseventsbackend.authorization.validation.Email;
import com.sportsevents.backend.sportseventsbackend.authorization.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@FieldMatch(first = "password", second = "repeatPassword")
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @Size(min = 6, max = 25)
    private String password;
    @Size(min = 6, max = 25)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String city;
    @NotBlank
    private String sex;
}
