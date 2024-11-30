package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.PersonCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.PersonResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.PersonUpdateRequest;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonRestMapper {
    Person toPerson(PersonCreateRequest personCreateRequest);
    Person toPerson(PersonUpdateRequest personUpdateRequest);
    PersonResponse toPersonResponse(Person person);
    List<PersonResponse> toPersonResponseList(List<Person> personList);
}
