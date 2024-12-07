package com.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tokens")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class TokenEntity extends BaseEntity {
    @Column(nullable = false, name = "token_name")
    private String key;

    @Column(nullable = false, columnDefinition = "TEXT" , name = "token_value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
