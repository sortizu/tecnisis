package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtisticEvaluationPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ArtisticEvaluationPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ArtisticEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
// implements persistence port

@Component
@RequiredArgsConstructor
public class ArtisticEvaluationPersistenceAdapter implements ArtisticEvaluationPersistencePort {

    private final ArtisticEvaluationRepository repository;
    private final ArtisticEvaluationPersistenceMapper mapper;

    @Override
    public Optional<ArtisticEvaluation> findById(Long id) {
        return repository.findById(id).map(mapper::toArtisticEvaluation);
    }

    @Override
    public Optional<ArtisticEvaluation> findByRequestId(Long id) {
        return repository.findByRequestId(id).map(mapper::toArtisticEvaluation);
    }

    @Override
    public List<ArtisticEvaluation> findAll() {
        return mapper.toArtisticEvaluationList(repository.findAll());
    }

    @Override
    public ArtisticEvaluation save(ArtisticEvaluation artisticEvaluation) {
        return mapper.toArtisticEvaluation(
                repository.save(mapper.toArtisticEvaluationEntity(artisticEvaluation)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
