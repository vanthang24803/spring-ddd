package com.amak.app.infrastructure.jpa;

import com.amak.app.domain.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
