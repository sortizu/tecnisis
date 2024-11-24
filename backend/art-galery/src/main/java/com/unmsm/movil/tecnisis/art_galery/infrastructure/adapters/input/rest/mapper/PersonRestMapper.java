package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.PersonCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.PersonResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonRestMapper {

    Person toPerson(PersonCreateRequest personCreateRequest);

    @Mapping(target = "user", source = "user")
    PersonResponse toPersonResponse(Person person);

    List<PersonResponse> toPersonResponseList(List<Person> personList);


    default UserResponse toUserResponse(com.unmsm.movil.tecnisis.art_galery.domain.model.User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
