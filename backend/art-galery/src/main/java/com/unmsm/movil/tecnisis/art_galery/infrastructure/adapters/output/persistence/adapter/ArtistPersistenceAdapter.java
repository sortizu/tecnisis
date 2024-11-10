package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ArtistPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

// implements the output por of application and use the repository
@Component
@RequiredArgsConstructor
public class ArtistPersistenceAdapter implements ArtistPersistencePort {

    private final ArtistRepository repository;
    private final ArtistPersistenceMapper mapper;

    @Override
    public Optional<Artist> findById(Long id) {
        return repository.findById(id).map(mapper::toArtist);
    }

    @Override
    public List<Artist> findAll() {
        return mapper.toArtistList(repository.findAll());
    }

    @Override
    public Artist save(Artist artist) {
        return mapper.toArtist(repository.save(mapper.toArtistEntity(artist)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
