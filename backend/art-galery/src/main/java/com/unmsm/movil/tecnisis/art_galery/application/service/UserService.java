package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.UserServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.UserPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.UserNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserRole findUserRoleById(Long id) {
        return userPersistencePort.findUserRolById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
