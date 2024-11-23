package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    @Mapping(target = "person.role", source = "person.role") // Mapear el campo role
    @Mapping(target = "person.name", source = "person.name")
    @Mapping(target = "person.dni", source = "person.dni")
    @Mapping(target = "person.address", source = "person.address")
    @Mapping(target = "person.gender", source = "person.gender")
    @Mapping(target = "person.phone", source = "person.phone")
    UserEntity toUserEntity(User user);

    @Mapping(target = "person.role", source = "person.role")
    @Mapping(target = "person.name", source = "person.name")
    @Mapping(target = "person.dni", source = "person.dni")
    @Mapping(target = "person.address", source = "person.address")
    @Mapping(target = "person.gender", source = "person.gender")
    @Mapping(target = "person.phone", source = "person.phone")
    User toUser(UserEntity userEntity);
}
