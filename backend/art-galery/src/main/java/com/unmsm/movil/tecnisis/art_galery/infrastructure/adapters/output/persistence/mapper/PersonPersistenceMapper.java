package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserPersistenceMapper.class})
public interface PersonPersistenceMapper {

    @Mapping(target = "user", ignore = true)
    PersonEntity toPersonEntity(Person person);

    @Mapping(target = "user", ignore = true)
    Person toPerson(PersonEntity personEntity);

    List<Person> toPersonList(List<PersonEntity> personEntityList);
}