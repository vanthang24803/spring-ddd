package com.amak.app.infrastructure.jpa;

import com.amak.app.domain.enums.RoleName;
import com.amak.app.domain.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleByName(RoleName roleEntity);

    @Query("""
                SELECT r.name
                FROM RoleEntity r
                JOIN r.userRoleEntities ur
                JOIN ur.userEntity u
                WHERE u.id = :param
            """)
    List<String> findRoleForAccount(@Param("param") Long id);

}
