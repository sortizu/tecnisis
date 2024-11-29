package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ManagerPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ManagerPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ManagerPersistenceAdapter implements ManagerPersistencePort {

    private final ManagerRepository managerRepository;
    private final ManagerPersistenceMapper managerMapper;

    @Override
    public Optional<Manager> findById(Long id) {
        return managerRepository.findById(id).map(managerMapper::toManager);
    }

    @Override
    public List<Manager> findAll() {
        return managerMapper.toManagerList(managerRepository.findAll());
    }

    @Override
    public Manager save(Manager manager) {
        return managerMapper.toManager(
                managerRepository.save(
                        managerMapper.toManagerEntity(manager)
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        managerRepository.deleteById(id);
    }
}
