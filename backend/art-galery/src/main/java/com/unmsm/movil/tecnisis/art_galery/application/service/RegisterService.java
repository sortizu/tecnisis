package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.LoginPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ManagerPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.InvalidRoleException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.*;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final LoginPersistencePort userPersistencePort;
    private final ArtistPersistencePort artistPersistencePort;
    private final ManagerPersistencePort managerPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
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

        User savedUser = userPersistencePort.save(user);

        switch (request.getUserRole().toLowerCase()) {
            case "artist":
                Artist artist = new Artist();
                artist.setPerson(savedUser.getPerson());
                artistPersistencePort.save(artist);
                break;
            case "manager":
                Manager manager = new Manager();
                manager.setPerson(savedUser.getPerson());
                managerPersistencePort.save(manager);
                break;
            case "art-evaluator":
            case "economic-evaluator":
                Specialist specialist = new Specialist();
                specialist.setPerson(savedUser.getPerson());
                specialist.setIsAvailable(true);
                specialistPersistencePort.save(specialist);
                break;
            default:
                throw new InvalidRoleException("Invalid role: " + request.getUserRole());
        }

        return savedUser;
    }
}


