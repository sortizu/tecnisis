package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<UserRole> findUserRolById(Long id);
}
