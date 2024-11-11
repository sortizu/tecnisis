package com.tecnisis.api.tecnisis.config;

import com.tecnisis.api.tecnisis.application.port.out.UserRepository;
import com.tecnisis.api.tecnisis.domain.model.AppUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Comprueba si ya existe un usuario con el email especificado para evitar duplicados
            userRepository.findByEmail("admin@example.com").ifPresentOrElse(
                    user -> System.out.println("Usuario de prueba ya existe: " + user.getEmail()),
                    () -> {
                        // Crear un nuevo usuario con la contraseña encriptada
                        String encodedPassword = passwordEncoder.encode("password"); // Reemplaza "password" con tu contraseña deseada
                        AppUser user = new AppUser(null, "admin@example.com", encodedPassword);
                        userRepository.save(user);
                        System.out.println("Usuario de prueba creado: " + user.getEmail());
                    }
            );
        };
    }
}
