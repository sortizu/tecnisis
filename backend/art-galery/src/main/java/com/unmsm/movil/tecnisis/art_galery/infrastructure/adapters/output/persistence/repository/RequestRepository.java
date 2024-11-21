package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Long>{
}
