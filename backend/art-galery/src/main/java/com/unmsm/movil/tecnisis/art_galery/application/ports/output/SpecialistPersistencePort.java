package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;

import java.util.List;
import java.util.Optional;

public interface SpecialistPersistencePort {
    Optional<Specialist> findById(Long id);
    List<Specialist> findAll();
    Specialist save(Specialist specialist);
    void deleteById(Long id);
}
