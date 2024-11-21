package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;

import java.util.List;

public interface RequestServicePort {
    Request findById(Long id);
    List<Request> findAll();
    Request save(Request request);
    Request update(Long id, Request request);
    void delete(Long id);
}
