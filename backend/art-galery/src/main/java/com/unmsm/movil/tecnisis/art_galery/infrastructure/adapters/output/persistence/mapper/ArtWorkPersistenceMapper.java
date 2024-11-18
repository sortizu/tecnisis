package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ArtWorkEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtWorkPersistenceMapper {
    ArtWorkEntity toArtWorkEntity(ArtWork artWork);
    ArtWork toArtWork(ArtWorkEntity artWorkEntity);
    List<ArtWork> toArtWorkList(List<ArtWorkEntity> artWorkEntityList);
}
