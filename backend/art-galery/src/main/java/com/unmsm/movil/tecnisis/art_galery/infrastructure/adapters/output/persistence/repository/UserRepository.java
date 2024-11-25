package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query(value = """
            WITH user_type AS (
                SELECT
                    u.id_user,
                    CASE
                        WHEN a.id_person IS NOT NULL THEN 'Artist'
                        WHEN s.id_person IS NOT NULL THEN 'Specialist'
                        WHEN m.id_person IS NOT NULL THEN 'Manager'
                        ELSE 'Unknown'
                    END AS user_role,
                    CASE
                        WHEN a.id_person IS NOT NULL THEN a.id_artist
                        WHEN s.id_person IS NOT NULL THEN s.id_specialist
                        WHEN m.id_person IS NOT NULL THEN m.id_manager
                        ELSE NULL
                    END AS role_id
                FROM
                    users u
                    LEFT JOIN persons p ON p.id_user = u.id_user
                    LEFT JOIN artists a ON a.id_person = p.id_person
                    LEFT JOIN specialists s ON s.id_person = p.id_person
                    LEFT JOIN managers m ON m.id_person = p.id_person
            )
            SELECT
                id_user,
                user_role,
                role_id
            FROM
                user_type
            WHERE
                id_user = :id
    """, nativeQuery = true)
    Optional<Map<String, Object>> findByRole(@Param("id") Long id);
}