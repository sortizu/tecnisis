package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.EconomicEvaluationPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.EconomicEvaluation;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.EconomicEvaluationPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.EconomicEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EconomicEvaluationPersistenceAdapter implements EconomicEvaluationPersistencePort {

    private final EconomicEvaluationRepository repository;
    private final EconomicEvaluationPersistenceMapper mapper;

    @Override
    public Optional<EconomicEvaluation> findById(Long id) {
        return repository.findById(id).map(mapper::toEconomicEvaluation);
    }

    @Override
    public List<EconomicEvaluation> findAll() {
        return mapper.toEconomicEvaluationList(repository.findAll());
    }

    @Override
    public EconomicEvaluation save(EconomicEvaluation economicEvaluation) {
        return mapper.toEconomicEvaluation(
                repository.save(mapper.toEconomicEvaluationEntity(economicEvaluation)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<EconomicEvaluation> findByRequestId(Long requestId) {
        return repository
                .findByRequestId(requestId)
                .map(mapper::toEconomicEvaluation);
    }

    @Override
    public List<EconomicEvaluation> findByStatus(String status) {
        return mapper.toEconomicEvaluationList(repository.findByStatus(status));
    }
}