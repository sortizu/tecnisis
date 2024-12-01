package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.EconomicEvaluation;

import java.util.List;
import java.util.Optional;

public interface EconomicEvaluationPersistencePort {
    Optional<EconomicEvaluation> findById(Long id);
    List<EconomicEvaluation> findAll();
    EconomicEvaluation save(EconomicEvaluation economicEvaluation);
    void deleteById(Long id);
    Optional<EconomicEvaluation> findByRequestId(Long requestId);
    List<EconomicEvaluation> findByStatus(String status);
}
