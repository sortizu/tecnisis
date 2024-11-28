package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ArtistEntity;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long>{
    @Query(value = """
        SELECT r
        FROM RequestEntity r
        JOIN r.artWork aw
        WHERE aw.artist.id = :artistId
    """)
    List<RequestEntity> findRequestsById(@Param("artistId") Long artistId);
}