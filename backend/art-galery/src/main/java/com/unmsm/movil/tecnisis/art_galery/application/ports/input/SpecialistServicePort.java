package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;

import java.util.List;

public interface SpecialistServicePort {
    Specialist findById(Long id);
    List<Specialist> findAll();
    Specialist save(Specialist specialist);
    Specialist update(Long id, Specialist specialist);
    void delete(Long id);
}
