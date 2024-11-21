package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.LoginPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.AccountLockedException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.InvalidCredentialsException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    private static final int MAX_ATTEMPTS = 5;

    // Usar ConcurrentHashMap para evitar problemas en ambientes multi-hilos
    private final Map<String, Integer> failedAttempts = new ConcurrentHashMap<>();

    public Optional<User> login(String email, String password) {
        // Buscar el usuario por email
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        // Validar si la cuenta está bloqueada
        if (failedAttempts.getOrDefault(email, 0) >= MAX_ATTEMPTS) {
            throw new AccountLockedException();
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            failedAttempts.put(email, failedAttempts.getOrDefault(email, 0) + 1);
            throw new InvalidCredentialsException();
        }

        // Reiniciar los intentos fallidos en caso de login exitoso
        failedAttempts.remove(email);
        return Optional.of(user);
    }
}
