package com.tecnisis.api.tecnisis.adapter.out.persistence;

import com.tecnisis.api.tecnisis.application.port.out.UserRepository;
import com.tecnisis.api.tecnisis.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<AppUser, Long>, UserRepository {

    @Override
    Optional<AppUser> findByEmail(String email);

    @Override
    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.person WHERE u.email = :email")
    Optional<AppUser> findByEmailWithPerson(String email); // Implementación del método para obtener AppUser con datos en 'persons'
}
