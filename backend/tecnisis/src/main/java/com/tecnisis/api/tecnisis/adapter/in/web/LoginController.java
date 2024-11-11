package com.tecnisis.api.tecnisis.adapter.in.web;

import com.tecnisis.api.tecnisis.adapter.in.web.dto.LoginRequest;
import com.tecnisis.api.tecnisis.application.port.in.LoginUseCase;
import com.tecnisis.api.tecnisis.domain.model.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AppUser> login(@RequestBody LoginRequest loginRequest) {
        AppUser user = loginUseCase.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user); // Retorna el usuario completo con la información de 'persons'
        }
        return ResponseEntity.status(401).build(); // Error de autenticación
    }
}
