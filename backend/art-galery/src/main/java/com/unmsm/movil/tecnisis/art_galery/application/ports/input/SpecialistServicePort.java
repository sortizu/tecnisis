package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;

import java.util.List;

public interface SpecialistServicePort {
    Specialist findById(Long id);
    List<Specialist> findAll();
    List<Request> findArtisticRequestsBySpecialistId(Long id);
    List<Request> findEconomicRequestsBySpecialistId(Long id);
    Specialist save(Specialist specialist);
    Specialist update(Long id, Specialist specialist);
    void delete(Long id);
    void addTechnique(Long specialistId, Long techniqueId);
}
