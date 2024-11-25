package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ManagerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManagerPersistenceMapper {
    ManagerEntity toEntity(Manager manager);
    Manager toDomain(ManagerEntity managerEntity);
}
