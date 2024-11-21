package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtWorkCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtWorkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ArtistRestMapper.class, TechniqueRestMapper.class})
public interface ArtWorkRestMapper {

    @Mapping(target = "artist", expression = "java(mapToArtist(artWorkCreateRequest.getArtistId()))")
    @Mapping(target = "technique", expression = "java(mapToTechnique(artWorkCreateRequest.getTechniqueId()))")
    ArtWork toArtWork(ArtWorkCreateRequest artWorkCreateRequest);

    ArtWorkResponse toArtWorkResponse(ArtWork artWork);

    List<ArtWorkResponse> toArtWorkResponseList(List<ArtWork> artWorkList);

    default Artist mapToArtist(Long id) {
        return Artist.builder()
                .id(id)
                .build();
    }
    default Technique mapToTechnique(Long id) {
        return Technique.builder()
                .id(id)
                .build();
    }
}
