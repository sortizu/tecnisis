package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.TechniqueCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.TechniqueResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechniqueRestMapper {
    Technique toTechnique(TechniqueCreateRequest techniqueCreateRequest);
    TechniqueResponse toTechniqueResponse(Technique technique);
    List<TechniqueResponse> toTechniqueResponseList(List<Technique> techniqueList);
}
