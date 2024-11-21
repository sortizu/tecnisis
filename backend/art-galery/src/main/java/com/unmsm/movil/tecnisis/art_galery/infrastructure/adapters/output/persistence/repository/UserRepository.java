package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
