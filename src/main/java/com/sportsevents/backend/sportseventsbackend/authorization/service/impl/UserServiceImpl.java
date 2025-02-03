package com.sportsevents.backend.sportseventsbackend.authorization.service.impl;

import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserDto;
import com.sportsevents.backend.sportseventsbackend.authorization.dto.user.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.authorization.exception.RegistrationException;
import com.sportsevents.backend.sportseventsbackend.authorization.mapper.UserMapper;
import com.sportsevents.backend.sportseventsbackend.authorization.model.Role;
import com.sportsevents.backend.sportseventsbackend.authorization.model.User;
import com.sportsevents.backend.sportseventsbackend.authorization.repository.RoleRepository;
import com.sportsevents.backend.sportseventsbackend.authorization.repository.UserRepository;
import com.sportsevents.backend.sportseventsbackend.authorization.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(UserRegistrationRequestDto requestDto) throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("This email address is already taken");
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = userMapper.toModel(requestDto);
        Role role = roleRepository.findByRole(Role.RoleName.ROLE_USER);
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return userMapper.toDto(user);
    }
}
