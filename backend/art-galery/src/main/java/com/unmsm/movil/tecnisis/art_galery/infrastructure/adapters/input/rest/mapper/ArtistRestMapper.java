package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtistCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtistResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PersonRestMapper.class})
public interface ArtistRestMapper {
    @Mapping(target = "person", expression = "java(mapToPerson(artistCreateRequest.getPersonId()))")
    Artist toArtist(ArtistCreateRequest artistCreateRequest);
    ArtistResponse toArtistResponse(Artist artist);
    List<ArtistResponse> toArtistResponseList(List<Artist> artistList);

    default Person mapToPerson(Long id) {
        return Person.builder()
                .id(id)
                .build();
    }
}
