package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtWorkServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ArtWorkRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtWorkCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtWorkResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artworks")
@Tag(name = "ArtWork Controller", description = "Endpoint to management artworks")
public class ArtWorkRestAdapter {
    private final ArtWorkServicePort servicePort;
    private final ArtWorkRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<ArtWorkResponse> findAll() {
        return restMapper.toArtWorkResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public ArtWorkResponse findById(@PathVariable Long id) {
        return restMapper.toArtWorkResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<ArtWorkResponse> save(@Valid @RequestBody ArtWorkCreateRequest request) {
        ArtWorkResponse response = restMapper.toArtWorkResponse(
                servicePort.save(restMapper.toArtWork(request)));
        return ResponseEntity
                .created(URI.create("/artworks/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public ArtWorkResponse update(@Valid @RequestBody ArtWorkCreateRequest request, @PathVariable Long id) {
        return restMapper.toArtWorkResponse(
                servicePort.update(id, restMapper.toArtWork(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
