package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;

import java.util.List;

public interface ArtWorkServicePort {
    ArtWork findById(Long id);
    List<ArtWork> findAll();
    ArtWork save(ArtWork artWork);
    ArtWork update(Long id, ArtWork artWork);
    void delete(Long id);
}
