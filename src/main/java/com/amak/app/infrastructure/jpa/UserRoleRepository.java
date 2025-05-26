package com.amak.app.infrastructure.jpa;

import com.amak.app.domain.models.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
