package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.User;

import java.util.Optional;

public interface LoginPersistencePort {
    Optional<User> findByEmail(String email);
    User save(User user);
}
