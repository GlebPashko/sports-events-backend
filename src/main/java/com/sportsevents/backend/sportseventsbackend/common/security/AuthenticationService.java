package com.sportsevents.backend.sportseventsbackend.common.security;

import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserLoginRequestDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                            requestDto.getPassword())
            );

            String token = jwtUtil.generateToken(authentication.getName());
            return new UserLoginResponseDto(token);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }
}

