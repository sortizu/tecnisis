package com.tecnisis.api.tecnisis.application.port.out;

import com.tecnisis.api.tecnisis.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByIdNumber(String idNumber);
}
