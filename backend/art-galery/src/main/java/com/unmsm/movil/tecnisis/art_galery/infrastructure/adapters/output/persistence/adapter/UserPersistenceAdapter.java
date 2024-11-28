package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.UserPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserPersistenceMapper userMapper;

    @Override
    public Optional<UserRole> findUserRolById(Long id) {
        return userRepository.findByRole(id)
                .map(UserPersistenceMapper.INSTANCE::toUserRole);
    }
}