package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ArtisticEvaluationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {SpecialistPersistenceMapper.class, RequestPersistenceMapper.class, DocumentPersistenceMapper.class})
public interface ArtisticEvaluationPersistenceMapper {
    ArtisticEvaluationEntity toArtisticEvaluationEntity(ArtisticEvaluation artisticEvaluation);
    ArtisticEvaluation toArtisticEvaluation(ArtisticEvaluationEntity artisticEvaluationEntity);
    List<ArtisticEvaluation> toArtisticEvaluationList(List<ArtisticEvaluationEntity> artisticEvaluationEntityList);
}
