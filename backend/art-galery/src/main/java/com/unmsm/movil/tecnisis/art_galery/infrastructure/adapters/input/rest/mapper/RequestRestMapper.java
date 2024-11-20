package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RequestCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.RequestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ArtWorkRestMapper.class})
public interface RequestRestMapper {
    @Mapping(target = "artWork", expression = "java(mapToArtWork(requestCreateRequest.getArtWorkId()))")
    Request toRequest(RequestCreateRequest requestCreateRequest);
    RequestResponse toRequestResponse(Request request);
    List<RequestResponse> toRequestResponseList(List<Request> requestList);

    default ArtWork mapToArtWork(Long id) {
        return ArtWork.builder()
                .id(id)
                .build();
    }
}
