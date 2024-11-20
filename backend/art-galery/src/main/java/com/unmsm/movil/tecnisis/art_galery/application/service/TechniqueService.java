package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.TechniqueServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.TechniquePersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.TechniqueNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class TechniqueService implements TechniqueServicePort {

    private final TechniquePersistencePort techniquePersistencePort;

    @Override
    public Technique findById(Long id) {
        return techniquePersistencePort
                .findById(id)
                .orElseThrow(TechniqueNotFoundException::new);
    }

    @Override
    public List<Technique> findAll() {
        return techniquePersistencePort
                .findAll();
    }

    @Override
    public Technique save(Technique technique) {
        return techniquePersistencePort.save(technique);
    }

    @Override
    public Technique update(Long id, Technique technique) {
        return techniquePersistencePort
                .findById(id)
                .map(techniqueToUpdate -> {
                    techniqueToUpdate.setName(technique.getName());
                    techniqueToUpdate.setDescription(technique.getDescription());
                    return techniquePersistencePort.save(techniqueToUpdate);
                })
                .orElseThrow(TechniqueNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (techniquePersistencePort.findById(id).isEmpty()) throw new TechniqueNotFoundException();
        techniquePersistencePort.deleteById(id);
    }
}
