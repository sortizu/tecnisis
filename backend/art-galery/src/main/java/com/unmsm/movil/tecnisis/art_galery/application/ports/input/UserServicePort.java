package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;

public interface UserServicePort {
    UserRole findUserRoleById(Long id);
}