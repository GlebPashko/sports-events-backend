package com.sportsevents.backend.sportseventsbackend.user.repository;

import com.sportsevents.backend.sportseventsbackend.user.model.Role;
import com.sportsevents.backend.sportseventsbackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Role.RoleName roleName);
}
