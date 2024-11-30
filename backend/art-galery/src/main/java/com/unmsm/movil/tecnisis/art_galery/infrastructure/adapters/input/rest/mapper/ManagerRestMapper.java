package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ManagerResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.ManagerUpdateRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonRestMapper.class})
public interface ManagerRestMapper {

    Manager toManager(ManagerUpdateRequest  managerUpdateRequest);
    ManagerResponse toManagerResponse(Manager manager);
    List<ManagerResponse> toManagerResponseList(List<Manager> managerList);
}
