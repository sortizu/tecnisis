package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.RequestEntity;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long>{

    @Query(value = """
        SELECT r
        FROM RequestEntity r
        JOIN r.artisticEvaluations ae
        WHERE ae.specialist.id = :id
    """)
    List<RequestEntity> findArtisticRequestsById(@Param("id") Long id);

    @Query(value = """
        SELECT r
        FROM RequestEntity r
        JOIN r.economicEvaluations ee
        WHERE ee.specialist.id = :id
    """)
    List<RequestEntity> findEconomicRequestsById(@Param("id") Long id);
}