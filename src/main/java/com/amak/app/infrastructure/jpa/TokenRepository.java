package com.amak.app.infrastructure.jpa;

import com.amak.app.domain.enums.TokenName;
import com.amak.app.domain.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("SELECT t FROM TokenEntity t WHERE t.userEntity.id = :userId")
    List<TokenEntity> findAllTokenByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM TokenEntity t WHERE t.userEntity.id = :userId AND t.name = :tokenName")
    Optional<TokenEntity> findUserTokenByName(@Param("userId") Long userId, @Param("tokenName") TokenName tokenName);

    Optional<TokenEntity> findTokenEntitiesByValue(String value);
}
