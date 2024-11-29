package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.PersonServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ManagerPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.PersonPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.PersonNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class PersonService implements PersonServicePort {

    private final PersonPersistencePort personPersistencePort;
    private final ArtistPersistencePort artistPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
    private final ManagerPersistencePort managerPersistencePort;

    @Override
    public Person findById(Long id) {
        return personPersistencePort
                .findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public List<Person> findAll() {
        return personPersistencePort
                .findAll();
    }

    @Override
    public Person save(Person person) {
        Person personToSave = personPersistencePort.save(person);
        switch (person.getRole()) {
            case "ARTIST" -> artistPersistencePort
                    .save(Artist.builder()
                            .person(personToSave)
                            .build()
                    );
            case "ART-EVALUATOR", "ECONOMIC-EVALUATOR" -> specialistPersistencePort
                    .save(Specialist.builder()
                            .person(personToSave)
                            .isAvailable(true)
                            .build()
                    );
            case "MANAGER" -> managerPersistencePort
                    .save(Manager.builder()
                            .person(personToSave)
                            .build()
                    );
            default -> throw new PersonNotFoundException();
        }
        return personToSave;
    }

    @Override
    public Person update(Long id, Person person) {

        return personPersistencePort
                .findById(id)
                .map(personToUpdate -> {
                    personToUpdate.setName(person.getName());
                    personToUpdate.setDni(person.getDni());
                    personToUpdate.setAddress(person.getAddress());
                    personToUpdate.setGender(person.getGender());
                    personToUpdate.setPhone(person.getPhone());
                    personToUpdate.setRole(person.getRole());
                    return personPersistencePort.save(personToUpdate);
                })
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (personPersistencePort.findById(id).isEmpty()) throw new PersonNotFoundException();
        personPersistencePort.deleteById(id);
    }
}
