package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import com.unmsm.movil.tecnisis.art_galery.domain.model.EconomicEvaluation;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.EconomicEvaluationCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.EconomicEvaluationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {SpecialistRestMapper.class, RequestRestMapper.class, DocumentRestMapper.class})
public interface EconomicEvaluationRestMapper {

    @Mapping(target = "specialist", expression = "java(mapToSpecialist(request.getSpecialistId()))")
    @Mapping(target = "request", expression = "java(mapToRequest(request.getRequestId()))")
    @Mapping(target = "document", expression = "java(mapToDocument(request.getDocumentId()))")
    EconomicEvaluation toEconomicEvaluation(EconomicEvaluationCreateRequest request);

    EconomicEvaluationResponse toEconomicEvaluationResponse(EconomicEvaluation economicEvaluation);

    List<EconomicEvaluationResponse> toEconomicEvaluationResponseList(List<EconomicEvaluation> economicEvaluationList);

    default Specialist mapToSpecialist(Long id) {
        return Specialist.builder()
                .id(id)
                .build();
    }

    default Request mapToRequest(Long id) {
        return Request.builder()
                .id(id)
                .build();
    }

    default Document mapToDocument(Long id) {
        return Document.builder()
                .id(id)
                .build();
    }
}