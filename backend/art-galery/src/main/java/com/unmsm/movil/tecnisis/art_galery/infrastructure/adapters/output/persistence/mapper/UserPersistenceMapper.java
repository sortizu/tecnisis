package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    @Mapping(target = "person.user", ignore = true)
    UserEntity toUserEntity(User user);

    @Mapping(target = "person.user", ignore = true)
    User toUser(UserEntity userEntity);
}





