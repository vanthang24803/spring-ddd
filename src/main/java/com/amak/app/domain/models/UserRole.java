package com.amak.app.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt = Instant.now();

    public  UserRole(User user, Role role) {
        this.role = role;
        this.user = user;
        this.assignedAt = Instant.now();
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserRoleId implements Serializable {
    private Long user;
    private Long role;
}
