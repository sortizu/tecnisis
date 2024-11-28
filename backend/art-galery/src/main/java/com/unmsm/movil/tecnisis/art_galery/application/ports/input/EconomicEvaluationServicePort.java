package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.EconomicEvaluation;

import java.util.List;

public interface EconomicEvaluationServicePort {
    EconomicEvaluation findById(Long id);
    List<EconomicEvaluation> findAll();
    EconomicEvaluation save(EconomicEvaluation economicEvaluation);
    EconomicEvaluation update(Long id, EconomicEvaluation economicEvaluation);
    void delete(Long id);

}