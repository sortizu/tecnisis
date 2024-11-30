package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import java.util.List;

public interface PersonServicePort {
    Person findById(Long id);
    List<Person> findAll();
    Person save(Person artist);
    Person update(Long id, Person artist);
    void delete(Long id);
}
