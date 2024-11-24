package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonPersistenceMapper {
    @Mapping(target = "user.person", ignore = true) // Evitar referencias circulares
    PersonEntity toPersonEntity(Person person);

    @Mapping(target = "user.person", ignore = true) // Evitar referencias circulares
    Person toPerson(PersonEntity personEntity);

    // Este método convierte una lista de entidades a una lista de objetos de dominio
    List<Person> toPersonList(List<PersonEntity> personEntityList);

    // Este método convierte una lista de objetos de dominio a una lista de entidades
    List<PersonEntity> toPersonEntityList(List<Person> personList);
}
