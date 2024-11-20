package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtistServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.input.SpecialistServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ArtistRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.SpecialistRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtistCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.SpecialistCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtistResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.SpecialistResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specialists")
@Tag(name = "Specialist Controller", description = "Endpoint to management specialists")
public class SpecialistRestAdapter {

    private final SpecialistServicePort servicePort;
    private final SpecialistRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<SpecialistResponse> findAll() {
        return restMapper.toSpecialistResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public SpecialistResponse findById(@PathVariable Long id) {
        return restMapper.toSpecialistResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<SpecialistResponse> save(@Valid @RequestBody SpecialistCreateRequest request) {
        SpecialistResponse response = restMapper.toSpecialistResponse(
                servicePort.save(restMapper.toSpecialist(request)));
        return ResponseEntity
                .created(URI.create("/specialists/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public SpecialistResponse update(@Valid @RequestBody SpecialistCreateRequest request, @PathVariable Long id) {
        return restMapper.toSpecialistResponse(
                servicePort.update(id, restMapper.toSpecialist(request))
        );
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
