package com.tecnisis.api.tecnisis.adapter.in.web;

import com.tecnisis.api.tecnisis.adapter.in.web.dto.RegisterRequest;
import com.tecnisis.api.tecnisis.application.port.out.PersonRepository;
import com.tecnisis.api.tecnisis.application.port.out.UserRepository;
import com.tecnisis.api.tecnisis.domain.model.AppUser;
import com.tecnisis.api.tecnisis.domain.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    private final UserRepository userRepository;
    private final PersonRepository personRepository; // Repositorio para Person
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo electrónico ya está en uso.");
        }

        // Cifrar la contraseña
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Crear la entidad AppUser
        AppUser appUser = new AppUser();
        appUser.setEmail(request.getEmail());
        appUser.setPassword(encodedPassword);

        // Crear y guardar la entidad Person primero
        Person person = new Person();
        person.setName(request.getName());
        person.setIdNumber(request.getIdNumber());
        person.setAddress(request.getAddress());
        person.setGender(request.getGender().charAt(0));
        person.setPhone(request.getPhone());
        person.setUserRole(request.getUserRole());

        // Guardar Person en la base de datos
        person = personRepository.save(person);

        // Asociar la persona al usuario y guardar el usuario en la base de datos
        appUser.setPerson(person);
        person.setAppUser(appUser);
        userRepository.save(appUser);

        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }
}
