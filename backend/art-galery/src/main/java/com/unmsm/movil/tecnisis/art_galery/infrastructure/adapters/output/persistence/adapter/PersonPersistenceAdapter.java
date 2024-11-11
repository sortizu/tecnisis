package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.PersonPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.PersonPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersonPersistenceAdapter implements PersonPersistencePort {

    private final PersonRepository personRepository;
    private final PersonPersistenceMapper personMapper;

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id).map(personMapper::toPerson);
    }

    @Override
    public List<Person> findAll() {
        return personMapper.toPersonList(personRepository.findAll());
    }

    @Override
    public Person save(Person artist) {
        return personMapper.toPerson(personRepository.save(personMapper.toPersonEntity(artist)));
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}
