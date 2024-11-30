package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ManagerServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ManagerPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ManagerNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService implements ManagerServicePort {

    private final ManagerPersistencePort managerPersistencePort;
    private final PersonService  personService;

    @Override
    public Manager findById(Long id) {
        return managerPersistencePort
                .findById(id)
                .orElseThrow(ManagerNotFoundException::new);
    }

    @Override
    public List<Manager> findAll() {
        return managerPersistencePort.findAll();
    }

    @Override
    public Manager update(Long id, Manager manager) {
        return managerPersistencePort.findById(id)
                .map(m -> {
                    m.setPerson(personService.update(m.getPerson().getId(), manager.getPerson()));
                    return managerPersistencePort.save(m);
                })
                .orElseThrow(ManagerNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (managerPersistencePort.findById(id).isEmpty()) throw new ManagerNotFoundException();
        managerPersistencePort.deleteById(id);
    }
}
