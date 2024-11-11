package com.tecnisis.api.tecnisis.application.port.out;

import com.tecnisis.api.tecnisis.domain.model.AppUser;

import java.util.Optional;

public interface UserRepository {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByEmailWithPerson(String email);
    AppUser save(AppUser user);
}
