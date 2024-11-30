package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.SpecialistCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.SpecialistResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.SpecialistUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonRestMapper.class, TechniqueRestMapper.class})
public interface SpecialistRestMapper {

    Specialist toSpecialist(SpecialistUpdateRequest specialistCreateRequest);
    SpecialistResponse toSpecialistResponse(Specialist specialist);
    List<SpecialistResponse> toSpecialistResponseList(List<Specialist> specialistList);
}
