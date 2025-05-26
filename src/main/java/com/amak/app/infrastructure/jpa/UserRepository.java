package com.amak.app.infrastructure.jpa;

import com.amak.app.application.dto.auth.ProfileResponse;
import com.amak.app.domain.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :param OR u.email = :param")
    Optional<UserEntity> findUserByUsernameOrEmail(@Param("param") String param);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String userName);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.username = :param OR u.email = :param")
    Boolean existsByUsernameOrEmail(@Param("param") String param);

    Optional<UserEntity> findUserByEmail(String email);

    Optional<UserEntity> findUserEntitiesById(Long id);

    Optional<UserEntity> findUserByUsername(String username);

    @Query("""
                SELECT new com.amak.app.application.dto.auth.ProfileResponse(
                    u.id,
                    u.username,
                    u.email,
                    u.firstName,
                    u.middleName,
                    u.lastName,
                    u.phoneNumber,
                    u.lastLogin,
                    u.timezone,
                    u.locale,
                    u.avatarUrl,
                    u.emailVerified,
                    u.status,
                    null
                )
                FROM UserEntity u
                WHERE u.username = :param OR u.email = :param
            """)
    Optional<ProfileResponse> findProfileByUsernameOrEmail(@Param("param") String param);
}
