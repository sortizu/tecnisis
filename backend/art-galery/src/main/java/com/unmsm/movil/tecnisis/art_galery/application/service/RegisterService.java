package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.LoginPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ManagerPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.*;
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
        Person person = new Person();
        person.setName(request.getName());
        person.setDni(request.getIdNumber());
        person.setAddress(request.getAddress());
        person.setGender(request.getGender());
        person.setPhone(request.getPhone());
        person.setRole(request.getUserRole());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPerson(person);

        return userPersistencePort.save(user);
    }
}