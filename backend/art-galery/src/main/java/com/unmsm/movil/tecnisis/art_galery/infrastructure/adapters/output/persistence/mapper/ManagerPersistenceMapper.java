package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ManagerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonPersistenceMapper.class})
public interface ManagerPersistenceMapper {

    ManagerEntity toManagerEntity(Manager manager);
    Manager toManager(ManagerEntity managerEntity);
    List<Manager> toManagerList(List<ManagerEntity> managerEntityList);
}
