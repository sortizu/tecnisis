package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.SpecialistPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpecialistPersistenceAdapter implements SpecialistPersistencePort {

    private final SpecialistRepository specialistRepository;
    private final SpecialistPersistenceMapper specialistPersistenceMapper;

    @Override
    public Optional<Specialist> findById(Long id) {
        return specialistRepository.findById(id).map(specialistPersistenceMapper::toSpecialist);
    }

    @Override
    public List<Specialist> findAll() {
        return specialistPersistenceMapper.toSpecialistList(specialistRepository.findAll());
    }

    @Override
    public Specialist save(Specialist specialist) {
        return specialistPersistenceMapper.toSpecialist(
                specialistRepository.save(
                        specialistPersistenceMapper.toSpecialistEntity(specialist)));
    }

    @Override
    public void deleteById(Long id) {
        specialistRepository.deleteById(id);
    }
}
