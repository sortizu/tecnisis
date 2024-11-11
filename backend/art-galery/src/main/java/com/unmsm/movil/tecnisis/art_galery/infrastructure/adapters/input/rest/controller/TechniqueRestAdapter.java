package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.TechniqueServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.TechniqueRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.TechniqueCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.TechniqueResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/techniques")
@Tag(name = "Technique Controller", description = "Endpoint to management techniques")
public class TechniqueRestAdapter {
    private final TechniqueServicePort servicePort;
    private final TechniqueRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<TechniqueResponse> findAll() {
        return restMapper.toTechniqueResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public TechniqueResponse findById(@PathVariable Long id) {
        return restMapper.toTechniqueResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<TechniqueResponse> save(@Valid @RequestBody TechniqueCreateRequest request){
        TechniqueResponse response = restMapper.toTechniqueResponse(
                servicePort.save(restMapper.toTechnique(request)));

        return ResponseEntity
                .created(URI.create("/techniques/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public TechniqueResponse update(@PathVariable Long id, @Valid @RequestBody TechniqueCreateRequest request){
        return restMapper.toTechniqueResponse(
                servicePort.update(id, restMapper.toTechnique(request))
        );
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
