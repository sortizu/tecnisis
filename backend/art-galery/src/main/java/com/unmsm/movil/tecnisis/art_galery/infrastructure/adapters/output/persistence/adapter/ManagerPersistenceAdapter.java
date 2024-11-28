package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ManagerPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Manager;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.ManagerEntity;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ManagerPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ManagerPersistenceAdapter implements ManagerPersistencePort {

    private final ManagerRepository managerRepository;
    private final ManagerPersistenceMapper managerMapper;

    @Override
    public Optional<Manager> findById(Long id) {
        return managerRepository.findById(id)
                .map(managerMapper::toDomain);
    }


    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll().stream()
                .map(managerMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Manager save(Manager manager) {
        ManagerEntity entity = managerMapper.toEntity(manager);
        ManagerEntity savedEntity = managerRepository.save(entity);
        return managerMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        managerRepository.deleteById(id);
    }
}
