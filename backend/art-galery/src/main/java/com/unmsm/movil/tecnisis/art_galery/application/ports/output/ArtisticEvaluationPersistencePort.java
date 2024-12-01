package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;

import java.util.List;
import java.util.Optional;

public interface ArtisticEvaluationPersistencePort {
    Optional<ArtisticEvaluation> findById(Long id);
    Optional<ArtisticEvaluation> findByRequestId(Long id);
    List<ArtisticEvaluation> findAll();
    ArtisticEvaluation save(ArtisticEvaluation artisticEvaluation);
    void deleteById(Long id);
}