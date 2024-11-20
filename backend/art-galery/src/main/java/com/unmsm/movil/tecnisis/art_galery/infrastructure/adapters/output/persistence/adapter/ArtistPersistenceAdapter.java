package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ArtistPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

// implements the output port of application and use the repository
@Component
@RequiredArgsConstructor
public class ArtistPersistenceAdapter implements ArtistPersistencePort {

    private final ArtistRepository artistRepository;
    private final ArtistPersistenceMapper artistMapper;

    @Override
    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id).map(artistMapper::toArtist);
    }

    @Override
    public List<Artist> findAll() {
        return artistMapper.toArtistList(artistRepository.findAll());
    }

    @Override
    public Artist save(Artist artist) {
        return artistMapper.toArtist(artistRepository.save(artistMapper.toArtistEntity(artist)));
    }

    @Override
    public void deleteById(Long id) {
        artistRepository.deleteById(id);
    }
}
