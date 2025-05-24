package com.amak.app.infrastructure.jpa;

import com.amak.app.domain.enums.RoleName;
import com.amak.app.domain.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(RoleName role);
}
