package com.tecnisis.api.tecnisis.adapter.out.persistence;

import com.tecnisis.api.tecnisis.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
