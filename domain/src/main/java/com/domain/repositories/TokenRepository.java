package com.domain.repositories;

import com.domain.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
}
