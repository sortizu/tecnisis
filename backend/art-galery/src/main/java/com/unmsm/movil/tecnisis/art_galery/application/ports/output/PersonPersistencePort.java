package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import java.util.List;
import java.util.Optional;

public interface PersonPersistencePort {
    Optional<Person> findById(Long id);
    List<Person> findAll();
    Person save(Person artist);
    void deleteById(Long id);
}
