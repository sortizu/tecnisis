package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ArtistPersistenceAdapter implements ArtistPersistencePort {
    @Override
    public Optional<Artist> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Artist> findAll() {
        return null;
    }

    @Override
    public Artist save(Artist artist) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
