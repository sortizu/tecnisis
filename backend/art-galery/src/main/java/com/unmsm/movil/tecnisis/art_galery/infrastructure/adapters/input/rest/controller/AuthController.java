package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.service.LoginService;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.jwt.JwtUtil;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.LoginRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final UserRestMapper userRestMapper;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest.getEmail(), loginRequest.getPassword())
                .map(user -> {
                    // Generar el token JWT
                    String token = jwtUtil.generateToken(user.getEmail());
                    LoginResponse response = userRestMapper.toLoginResponse(user);
                    response.setToken(token); // Añadir el token a la respuesta
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(401).build());
    }
}
