package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerPersistencePort {

    Optional<Manager> findById(Long id);
    List<Manager> findAll();
    Manager save(Manager manager);
    void deleteById(Long id);
}
