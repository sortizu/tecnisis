package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.TechniquePersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.TechniquePersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.TechniqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TechniquePesistenceAdapter implements TechniquePersistencePort {

    private final TechniqueRepository techniqueRepository;
    private final TechniquePersistenceMapper techniqueMapper;

    @Override
    public Optional<Technique> findById(Long id) {
        return techniqueRepository.findById(id).map(techniqueMapper::toTechnique);
    }

    @Override
    public List<Technique> findAll() {
        return techniqueMapper.toTechniqueList(techniqueRepository.findAll());
    }

    @Override
    public Technique save(Technique technique) {
        return techniqueMapper.toTechnique(
                techniqueRepository.save(techniqueMapper.toTechniqueEntity(technique)));
    }

    @Override
    public void deleteById(Long id) {
        techniqueRepository.deleteById(id);
    }
}
