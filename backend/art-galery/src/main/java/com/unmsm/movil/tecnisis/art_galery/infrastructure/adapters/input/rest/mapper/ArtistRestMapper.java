package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtistCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtistResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.ArtistUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonRestMapper.class})
public interface ArtistRestMapper {
    Artist toArtist(ArtistUpdateRequest artistUpdateRequest);
    ArtistResponse toArtistResponse(Artist artist);
    List<ArtistResponse> toArtistResponseList(List<Artist> artistList);
}