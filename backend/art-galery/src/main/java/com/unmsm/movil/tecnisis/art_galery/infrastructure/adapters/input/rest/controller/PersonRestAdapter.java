package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.PersonServicePort;
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
@RequestMapping("/persons")
@Tag(name = "Person Controller", description = "Endpoint to management persons")
public class PersonRestAdapter {
    private final PersonServicePort servicePort;
    private final PersonRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<PersonResponse> findAll() {
        return restMapper.toPersonResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public PersonResponse findById(@PathVariable Long id) {
        return restMapper.toPersonResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<PersonResponse> save(@Valid @RequestBody PersonCreateRequest request) {
        PersonResponse response = restMapper.toPersonResponse(
                servicePort.save(restMapper.toPerson(request)));
        return ResponseEntity
                .created(URI.create("/persons/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public PersonResponse update(@Valid @RequestBody PersonCreateRequest request, @PathVariable Long id) {
        return restMapper.toPersonResponse(
                servicePort.update(id, restMapper.toPerson(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
