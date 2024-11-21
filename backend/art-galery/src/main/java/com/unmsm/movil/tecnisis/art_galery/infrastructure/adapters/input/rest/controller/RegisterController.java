package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.service.RegisterService;
import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = registerService.register(registerRequest);
        return ResponseEntity.ok(user);
    }
}
