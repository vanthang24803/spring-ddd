package com.domain.repositories;

import com.domain.enums.ERole;
import com.domain.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    @Query("SELECT r FROM RoleEntity r WHERE r.Role = :role")
    Optional<RoleEntity> findByRole(@Param("role") ERole role);

}
