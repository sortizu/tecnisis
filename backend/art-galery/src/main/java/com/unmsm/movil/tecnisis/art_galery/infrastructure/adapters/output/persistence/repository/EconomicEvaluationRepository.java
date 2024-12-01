package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.domain.model.EconomicEvaluation;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.EconomicEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EconomicEvaluationRepository extends JpaRepository<EconomicEvaluationEntity, Long> {

    @Query(value = """
        SELECT ee
        FROM EconomicEvaluationEntity ee
        JOIN FETCH ee.request r
        WHERE ee.request.id = :requestId
    """)
    Optional<EconomicEvaluationEntity> findByRequestId(Long requestId);
    List<EconomicEvaluationEntity> findByStatus(String status);
}