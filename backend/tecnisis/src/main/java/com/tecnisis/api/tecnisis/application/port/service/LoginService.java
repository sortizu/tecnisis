package com.tecnisis.api.tecnisis.application.port.service;

import com.tecnisis.api.tecnisis.application.port.in.LoginUseCase;
import com.tecnisis.api.tecnisis.application.port.out.UserRepository;
import com.tecnisis.api.tecnisis.domain.model.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser login(String email, String password) {
        Optional<AppUser> user = userRepository.findByEmailWithPerson(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        return null; // O lanza una excepción si prefieres
    }
}
