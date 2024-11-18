package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;

import java.util.List;

public interface TechniqueServicePort {
    Technique findById(Long id);
    List<Technique> findAll();
    Technique save(Technique technique);
    Technique update(Long id, Technique technique);
    void delete(Long id);
}
