package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtisticEvaluationCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtisticEvaluationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {SpecialistRestMapper.class, RequestRestMapper.class, DocumentRestMapper.class})
public interface ArtisticEvaluationRestMapper {
    
    @Mapping(target = "document", expression = "java(mapToDocument(artisticEvaluationCreateRequest.getDocumentId()))")
    ArtisticEvaluation  toArtisticEvaluation(ArtisticEvaluationCreateRequest artisticEvaluationCreateRequest);

    ArtisticEvaluationResponse  toArtisticEvaluationResponse(ArtisticEvaluation artisticEvaluation);

    List<ArtisticEvaluationResponse> toArtisticEvaluationResponseList(List<ArtisticEvaluation> artisticEvaluationList);

    default Specialist mapToSpecialist(Long id) {
        if (id == null) return null;

        return Specialist.builder()
                .id(id)
                .build();
    }

    default Request mapToRequest(Long id) {
        if (id == null) return null;
        return Request.builder()
                .id(id)
                .build();
    }

    default Document mapToDocument(Long id) {
        if (id == null) return null;
        return Document.builder()
                .id(id)
                .build();
    }
}