package com.sportsevents.backend.sportseventsbackend.user.service.impl;

import com.sportsevents.backend.sportseventsbackend.cart.service.ShoppingCartService;
import com.sportsevents.backend.sportseventsbackend.user.dto.AddRoleToUserRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserDto;
import com.sportsevents.backend.sportseventsbackend.user.dto.UserRegistrationRequestDto;
import com.sportsevents.backend.sportseventsbackend.user.exception.RegistrationException;
import com.sportsevents.backend.sportseventsbackend.user.mapper.UserMapper;
import com.sportsevents.backend.sportseventsbackend.user.model.Role;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import com.sportsevents.backend.sportseventsbackend.user.repository.RoleRepository;
import com.sportsevents.backend.sportseventsbackend.user.repository.UserRepository;
import com.sportsevents.backend.sportseventsbackend.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
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

        shoppingCartService.addShoppingCartToUser(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUser() {
        User user = getAuthenticatedUser();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id: " + id + " not found")
        );
        return userMapper.toDto(user);
    }

    @Override
    public void addRoleToUser(AddRoleToUserRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        try {
            Role.RoleName roleName = Role.RoleName.valueOf(requestDto.getRole());

            if (user.getRoles().stream().anyMatch(role -> role.getRole().equals(roleName))) {
                throw new EntityNotFoundException("This user already has this role");
            }

            Role organizerRole = roleRepository.findByRole(roleName);
            if (organizerRole == null) {
                throw new EntityNotFoundException("Role not found: " + requestDto.getRole());
            }

            user.getRoles().add(organizerRole);
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + requestDto.getRole());
        }
    }

    private User getAuthenticatedUser() {
        String userEmail = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with email: " + userEmail));
    }
}
