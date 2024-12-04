package com.domain.models;


import com.domain.enums.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @Column(name = "role_name")
    private ERole Role;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
}
