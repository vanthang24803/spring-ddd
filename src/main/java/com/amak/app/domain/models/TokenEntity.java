package com.amak.app.domain.models;

import com.amak.app.domain.enums.TokenName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TokenEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(length = 100, unique = true)
    private TokenName name;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "check_sum", nullable = false)
    private String checkSum;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "expired_at", nullable = false)
    private Instant expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
