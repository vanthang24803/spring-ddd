package com.amak.app.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "user_roles")
@IdClass(UserRole.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt = Instant.now();

    public UserRoleEntity(UserEntity userEntity, RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
        this.userEntity = userEntity;
        this.assignedAt = Instant.now();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserRole implements Serializable {
    private Long userEntity;
    private Long roleEntity;
}
