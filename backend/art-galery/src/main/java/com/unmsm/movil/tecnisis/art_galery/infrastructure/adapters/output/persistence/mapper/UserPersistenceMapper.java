package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    User toUser(UserEntity userEntity);
}
