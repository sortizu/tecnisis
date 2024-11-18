package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ArtistEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PersonPersistenceMapper.class})
public interface ArtistPersistenceMapper {
    ArtistEntity toArtistEntity(Artist artist);
    Artist toArtist(ArtistEntity artistEntity);
    List<Artist> toArtistList(List<ArtistEntity> artistEntityList);
}
