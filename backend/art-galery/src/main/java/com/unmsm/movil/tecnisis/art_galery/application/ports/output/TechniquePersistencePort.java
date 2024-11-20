package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;

import java.util.List;
import java.util.Optional;

public interface TechniquePersistencePort {
    Optional<Technique> findById(Long id);
    List<Technique> findAll();
    Technique save(Technique technique);
    void deleteById(Long id);
}
