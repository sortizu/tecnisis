package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;
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