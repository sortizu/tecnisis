package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;

import java.util.List;

public interface ArtisticEvaluationServicePort {
    ArtisticEvaluation findById(Long id);
    List<ArtisticEvaluation> findAll();
    ArtisticEvaluation update(Long id, ArtisticEvaluation artisticEvaluation);
    ArtisticEvaluation findByRequestId(Long id);
    void delete(Long id);
}
