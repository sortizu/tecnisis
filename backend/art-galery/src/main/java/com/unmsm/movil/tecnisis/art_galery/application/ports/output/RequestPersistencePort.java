package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestPersistencePort {
    Optional<Request> findById(Long id);
    List<Request> findAll();
    Request save(Request request);
    void deleteById(Long id);
}
