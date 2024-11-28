package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    UserPersistenceMapper INSTANCE = Mappers.getMapper(UserPersistenceMapper.class);

    @Mapping(source = "id_user", target = "userId", qualifiedByName = "toLong")
    @Mapping(source = "user_role", target = "role",  qualifiedByName = "toString")
    @Mapping(source = "role_id", target = "roleId", qualifiedByName = "toLong")
    UserRole toUserRole(Map<String, Object> map);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "person", target = "person")
    User toUser(UserEntity userEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "person", target = "person")
    UserEntity toUserEntity(User user);

    // Método para convertir un valor genérico a Long
    @Named("toLong")
    default Long toLong(Object value) {
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        throw new IllegalArgumentException("Cannot convert " + value + " to Long");
    }

    // Método para convertir un valor genérico a String
    @Named("toString")
    default String toString(Object value) {
        if (value == null) return null;
        return value.toString();
    }
}