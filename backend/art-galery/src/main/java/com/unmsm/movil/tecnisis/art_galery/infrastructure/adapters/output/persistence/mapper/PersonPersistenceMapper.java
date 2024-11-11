package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.PersonEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonPersistenceMapper {
    PersonEntity toPersonEntity(Person person);
    Person toPerson(PersonEntity personEntity);
    List<Person> toPersonList(List<PersonEntity> personEntityList);
}
