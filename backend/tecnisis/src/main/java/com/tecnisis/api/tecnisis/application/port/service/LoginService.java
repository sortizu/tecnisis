package com.tecnisis.api.tecnisis.application.port.service;

import com.tecnisis.api.tecnisis.adapter.in.web.dto.RegisterRequest;
import com.tecnisis.api.tecnisis.application.port.in.LoginUseCase;
import com.tecnisis.api.tecnisis.application.port.out.UserRepository;
import com.tecnisis.api.tecnisis.domain.model.AppUser;
import com.tecnisis.api.tecnisis.domain.model.Person;
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

    public AppUser register(RegisterRequest request) {
        Optional<AppUser> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("El usuario con ese correo ya existe");
        }

        // Encriptar la contraseña
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Crear AppUser
        AppUser appUser = new AppUser(null, request.getEmail(), encodedPassword);

        // Crear Person vinculado a AppUser
        Person person = new Person();
        person.setName(request.getName());
        person.setIdNumber(request.getIdNumber());
        person.setAddress(request.getAddress());
        person.setGender(request.getGender().charAt(0));
        person.setPhone(request.getPhone());
        person.setUserRole(request.getUserRole());
        person.setAppUser(appUser);

        // Guardar en la base de datos
        appUser.setPerson(person);
        return userRepository.save(appUser);
    }

}
