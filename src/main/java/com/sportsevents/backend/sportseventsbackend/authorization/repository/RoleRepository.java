package com.sportsevents.backend.sportseventsbackend.authorization.repository;

import com.sportsevents.backend.sportseventsbackend.authorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(Role.RoleName roleName);
}
