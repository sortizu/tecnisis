package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.RequestPersistenceMapper;
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
    private final SpecialistPersistenceMapper specialistMapper;
    private final RequestPersistenceMapper  requestMapper;

    @Override
    public Optional<Specialist> findById(Long id) {
        return specialistRepository.findById(id).map(specialistMapper::toSpecialist);
    }

    @Override
    public List<Specialist> findAll() {
        return specialistMapper.toSpecialistList(specialistRepository.findAll());
    }

    @Override
    public List<Request> findArtisticRequestsBySpecialistId(Long id) {
        return requestMapper.toRequestList(specialistRepository.findArtisticRequestsById(id));
    }

    @Override
    public List<Request> findEconomicRequestsBySpecialistId(Long id) {
        return requestMapper.toRequestList(specialistRepository.findEconomicRequestsById(id));
    }

    @Override
    public Specialist save(Specialist specialist) {
        return specialistMapper.toSpecialist(
                specialistRepository.save(
                        specialistMapper.toSpecialistEntity(specialist)));
    }

    @Override
    public void deleteById(Long id) {
        specialistRepository.deleteById(id);
    }
}