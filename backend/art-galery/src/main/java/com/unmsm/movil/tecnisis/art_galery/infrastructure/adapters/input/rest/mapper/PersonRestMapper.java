package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.PersonCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonRestMapper {
    //@Mapping(target = "id", source = "id")
    Person toPerson(PersonCreateRequest personCreateRequest);
    PersonResponse toPersonResponse(Person person);
    List<PersonResponse> toPersonResponseList(List<Person> personList);
}
