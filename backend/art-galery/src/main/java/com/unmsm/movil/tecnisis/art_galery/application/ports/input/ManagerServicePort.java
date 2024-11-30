package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;

import java.util.List;

public interface ManagerServicePort {

    Manager findById(Long id);
    List<Manager> findAll();
    Manager update(Long id, Manager manager);
    void delete(Long id);
}
