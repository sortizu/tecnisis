package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.EconomicEvaluation;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.EconomicEvaluationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        SpecialistPersistenceMapper.class,
        RequestPersistenceMapper.class,
        DocumentPersistenceMapper.class
})
public interface EconomicEvaluationPersistenceMapper {

    EconomicEvaluationEntity toEconomicEvaluationEntity(EconomicEvaluation economicEvaluation);
    EconomicEvaluation toEconomicEvaluation(EconomicEvaluationEntity economicEvaluationEntity);
    List<EconomicEvaluation> toEconomicEvaluationList(List<EconomicEvaluationEntity> economicEvaluationEntityList);
}