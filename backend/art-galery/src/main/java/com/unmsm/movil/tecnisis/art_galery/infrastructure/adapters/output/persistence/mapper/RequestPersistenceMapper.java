package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ArtWorkPersistenceMapper.class)
public interface RequestPersistenceMapper {

    @Mapping(target = "artisticEvaluations", ignore = true)
    @Mapping(target = "economicEvaluations", ignore = true)
    RequestEntity toRequestEntity(Request request);

    Request toRequest(RequestEntity requestEntity);
    List<Request> toRequestList(List<RequestEntity> requestEntityList);
}