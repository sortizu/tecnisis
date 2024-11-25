package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.UserServicePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.UserRole;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Endpoint to management users")
public class UserRestAdapter {

    private final UserServicePort servicePort;

    @GetMapping("/v1/api/rol-by-id/{id}")
    public UserRole getUserRoleById(@PathVariable Long id){
        return servicePort.findUserRoleById(id);
    }
}
