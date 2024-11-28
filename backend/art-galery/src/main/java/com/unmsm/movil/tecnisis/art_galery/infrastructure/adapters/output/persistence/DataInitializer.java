package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence;

import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.PersonEntity;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.PersonRepository;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByEmail("admin@example.com").ifPresentOrElse(
                    user -> System.out.println("Usuario de prueba ya existe: " + user.getEmail()),
                    () -> {
                        // Crear un usuario
                        String encodedPassword = passwordEncoder.encode("password");
                        UserEntity user = new UserEntity();
                        user.setEmail("admin@example.com");
                        user.setPassword(encodedPassword);
                        userRepository.save(user);
                        System.out.println("Usuario de prueba creado: " + user.getEmail());

                        // Crear una persona asociada al usuario
                        /*PersonEntity person = new PersonEntity();
                        person.setName("Admin User");
                        person.setDni("12345678"); // Valor de ejemplo para `dni`
                        person.setAddress("123 Admin Street");
                        person.setGender("M");
                        person.setPhone("123456789");
                        person.setRole("MANAGER");
                        person.setUser(user);
                        personRepository.save(person);
                        System.out.println("Persona asociada creada: " + person.getName());*/
                    }
            );
        };
    }
}
