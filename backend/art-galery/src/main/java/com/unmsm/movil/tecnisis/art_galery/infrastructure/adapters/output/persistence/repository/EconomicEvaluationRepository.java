package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.EconomicEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EconomicEvaluationRepository extends JpaRepository<EconomicEvaluationEntity, Long> {
    Optional<EconomicEvaluationEntity> findByRequestId(Long requestId);
}