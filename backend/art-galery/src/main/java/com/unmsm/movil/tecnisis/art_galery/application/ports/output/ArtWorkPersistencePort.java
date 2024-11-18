package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;

import java.util.List;
import java.util.Optional;

public interface ArtWorkPersistencePort {
    Optional<ArtWork> findById(Long id);
    List<ArtWork> findAll();
    ArtWork save(ArtWork artWork);
    void deleteById(Long id);
}
