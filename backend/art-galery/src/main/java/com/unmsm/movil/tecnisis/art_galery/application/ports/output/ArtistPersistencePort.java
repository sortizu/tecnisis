package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistPersistencePort {
    Optional<Artist> findById(Long id);
    List<Artist> findAll();
    Artist save(Artist artist);
    void deleteById(Long id);
}
