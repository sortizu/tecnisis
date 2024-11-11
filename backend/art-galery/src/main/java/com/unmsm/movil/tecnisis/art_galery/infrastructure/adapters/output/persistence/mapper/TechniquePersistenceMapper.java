package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.TechniqueEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechniquePersistenceMapper {
    TechniqueEntity toTechniqueEntity(Technique technique);
    Technique toTechnique(TechniqueEntity techniqueEntity);
    List<Technique> toTechniqueList(List<TechniqueEntity> techniqueEntityList);
}
