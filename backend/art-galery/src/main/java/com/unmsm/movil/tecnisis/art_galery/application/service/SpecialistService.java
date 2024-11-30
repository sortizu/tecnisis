package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.SpecialistServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.PersonPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.TechniquePersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.PersonNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.SpecialistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.TechniqueNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class SpecialistService implements SpecialistServicePort {

    private final SpecialistPersistencePort  specialistPersistencePort;
    private final PersonService personService;
    private final TechniquePersistencePort techniquePersistencePort;

    @Override
    public Specialist findById(Long id) {
        return specialistPersistencePort
                .findById(id)
                .orElseThrow(SpecialistNotFoundException::new);
    }

    @Override
    public List<Specialist> findAll() {
        return specialistPersistencePort.findAll();
    }

    @Override
    public List<Request> findArtisticRequestsBySpecialistId(Long id) {
        return specialistPersistencePort.findById(id)
                .map(s -> specialistPersistencePort.findArtisticRequestsBySpecialistId(s.getId()))
                .orElseThrow(SpecialistNotFoundException::new);
    }

    @Override
    public List<Request> findEconomicRequestsBySpecialistId(Long id) {
        return specialistPersistencePort.findById(id)
                .map(s -> specialistPersistencePort.findEconomicRequestsBySpecialistId(s.getId()))
                .orElseThrow(SpecialistNotFoundException::new);
    }

    @Override
    public Specialist update(Long id, Specialist request) {
        return specialistPersistencePort.findById(id)
                .map(s -> {
                    s.setPerson(personService.update(s.getPerson().getId(), request.getPerson()));
                    return specialistPersistencePort.save(s);
                })
                .orElseThrow(SpecialistNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (specialistPersistencePort.findById(id).isEmpty()) throw new SpecialistNotFoundException();
        specialistPersistencePort.deleteById(id);
    }

    @Override
    public void addTechnique(Long specialistId, Long techniqueId) {
        Specialist specialist = specialistPersistencePort.findById(specialistId)
                .orElseThrow(SpecialistNotFoundException::new);

        Technique technique = techniquePersistencePort.findById(techniqueId)
                .orElseThrow(TechniqueNotFoundException::new);

        if (!specialist.getTechniques().contains(technique)) {
            specialist.getTechniques().add(technique);
            specialistPersistencePort.save(specialist);
        }
    }
}
