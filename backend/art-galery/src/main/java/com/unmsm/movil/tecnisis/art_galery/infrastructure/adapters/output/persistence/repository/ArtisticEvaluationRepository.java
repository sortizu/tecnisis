package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ArtisticEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtisticEvaluationRepository extends JpaRepository<ArtisticEvaluationEntity, Long>{

    @Query(value = """
        SELECT ae
        FROM ArtisticEvaluationEntity ae
        JOIN FETCH ae.request r
        WHERE ae.request.id = :requestId
    """)
    Optional<ArtisticEvaluationEntity> findByRequestId(@Param("requestId") Long requestId);
}