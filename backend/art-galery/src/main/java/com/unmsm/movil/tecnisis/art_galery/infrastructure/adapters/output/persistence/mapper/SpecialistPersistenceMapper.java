package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.SpecialistEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonPersistenceMapper.class,
        TechniquePersistenceMapper.class})
public interface SpecialistPersistenceMapper {
    SpecialistEntity toSpecialistEntity(Specialist specialist);
    Specialist toSpecialist(SpecialistEntity specialistEntity);
    List<Specialist> toSpecialistList(List<SpecialistEntity> specialistEntityList);
}