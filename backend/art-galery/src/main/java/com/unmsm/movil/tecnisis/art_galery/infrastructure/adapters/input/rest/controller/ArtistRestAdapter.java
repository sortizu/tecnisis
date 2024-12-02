package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtistServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ArtistRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.RequestRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtistResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.RequestResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.ArtistUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists/v1/api")
@Tag(name = "Artist Controller", description = "Endpoint to management artists")
public class ArtistRestAdapter {

    private final ArtistServicePort servicePort;
    private final ArtistRestMapper artistMapper;
    private final RequestRestMapper requestMapper;

    @GetMapping
    public List<ArtistResponse> findAll() {
        return artistMapper.toArtistResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public ArtistResponse findById(@PathVariable Long id) {
        return artistMapper.toArtistResponse(servicePort.findById(id));
    }

    @GetMapping("/requests/{id}")
    public List<RequestResponse> findRequestsById(@PathVariable Long id) {
        return requestMapper.toRequestResponseList(servicePort.findRequestsById(id));
    }

    @PutMapping("/{id}")
    public ArtistResponse update(@Valid @RequestBody ArtistUpdateRequest request, @PathVariable Long id) {
        return artistMapper.toArtistResponse(
                servicePort.update(id, artistMapper.toArtist(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}