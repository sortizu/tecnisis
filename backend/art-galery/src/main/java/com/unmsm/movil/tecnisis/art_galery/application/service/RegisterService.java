package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.LoginPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.domain.model.User;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);

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
        person.setUser(user);

        log.info("Before save - User entity: {}", user);
        log.info("Before save - Person entity: {}", person);
        log.info("Person's User: {}", person.getUser());
        log.info("User's Person: {}", user.getPerson());

        User savedUser = userPersistencePort.save(user);

        log.info("After save - User entity: {}", savedUser);
        log.info("After save - Person entity: {}", savedUser.getPerson());
        log.info("Person's User after save: {}", savedUser.getPerson().getUser());

        return savedUser;
    }


}

