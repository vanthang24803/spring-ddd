package com.amak.app.domain.models;

import com.amak.app.domain.enums.AccountStatus;
import com.amak.app.domain.enums.Locale;
import com.amak.app.domain.enums.Timezone;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String username;

    @NaturalId
    @Email
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "middle_name", length = 50)
    private String middleName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @NotBlank
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "email_verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Builder.Default
    private Boolean emailVerified = false;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Builder.Default
    private AccountStatus status = AccountStatus.INACTIVE;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "timezone", length = 50)
    @Builder.Default
    private Timezone timezone = Timezone.UTC_8;

    @Enumerated(EnumType.STRING)
    @Column(name = "locale", length = 10)
    @Builder.Default
    private Locale locale = Locale.US;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRoleEntity> userRoleEntities = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TokenEntity> tokens = new HashSet<>();
}
