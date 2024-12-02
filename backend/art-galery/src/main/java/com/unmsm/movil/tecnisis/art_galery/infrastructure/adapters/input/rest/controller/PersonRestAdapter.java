package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.PersonServicePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.PersonRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.PersonCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.PersonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons/v1/api")
@Tag(name = "Person Controller", description = "Endpoint to management persons")
public class PersonRestAdapter {

    private final PersonServicePort servicePort;
    private final PersonRestMapper restMapper;

    @GetMapping
    public List<PersonResponse> findAll() {
        return restMapper.toPersonResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public PersonResponse findById(@PathVariable Long id) {
        return restMapper.toPersonResponse(servicePort.findById(id));
    }

    @PostMapping
    public ResponseEntity<PersonResponse> save(@Valid @RequestBody PersonCreateRequest request) {
        Person personSaved = servicePort.save(restMapper.toPerson(request));
        PersonResponse response = restMapper.toPersonResponse(personSaved);
        return ResponseEntity
                .created(URI.create(response.getRole() + "/" + personSaved.getRolId()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}