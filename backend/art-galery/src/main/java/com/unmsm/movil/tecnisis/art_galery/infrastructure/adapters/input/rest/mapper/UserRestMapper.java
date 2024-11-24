package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.LoginRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    @Mapping(target = "person.user", ignore = true)
    User toUser(LoginRequest loginRequest);

    @Mapping(source = "person.dni", target = "dni")
    @Mapping(source = "person.name", target = "name")
    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.gender", target = "gender")
    @Mapping(source = "person.phone", target = "phone")
    @Mapping(source = "person.role", target = "role")
    LoginResponse toLoginResponse(User user);
}

