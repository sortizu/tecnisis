package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.LoginPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final LoginPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        // Verificar si el usuario ya existe
        userPersistencePort.findByEmail(request.getEmail()) // Cambié registerRequest por request
                .ifPresent(user -> {
                    throw new IllegalStateException("El correo ya está registrado");
                });

        // Crear el usuario
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Crear la persona vinculada
        Person person = new Person();
        person.setName(request.getName());
        person.setDni(request.getIdNumber());
        person.setAddress(request.getAddress());
        person.setGender(request.getGender());
        person.setPhone(request.getPhone());
        person.setRole(request.getUserRole());

        // Asociar persona con usuario
        user.setPerson(person);

        // Guardar el usuario
        return userPersistencePort.save(user);
    }
}
