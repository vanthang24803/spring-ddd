package com.amak.app.infrastructure.jpa;

import com.amak.app.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :param OR u.email = :param")
    Optional<User> findUserByUsernameOrEmail(@Param("param") String param);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String userName);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :param OR u.email = :param")
    Boolean existsByUsernameOrEmail(@Param("param") String param);

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);

}
