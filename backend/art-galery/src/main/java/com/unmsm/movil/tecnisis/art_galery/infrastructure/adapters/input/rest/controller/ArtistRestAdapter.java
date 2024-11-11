package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtistServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ArtistRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtistCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtistResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController @Tag(name = "Artist Controller", description = "Endpoint to management artists")
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistRestAdapter {

    private final ArtistServicePort servicePort;
    private final ArtistRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<ArtistResponse> findAll() {
        return restMapper.toArtistResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public ArtistResponse findById(@PathVariable Long id) {
        return restMapper.toArtistResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<ArtistResponse> save(@Valid @RequestBody ArtistCreateRequest request) {
        ArtistResponse response = restMapper.toArtistResponse(
                        servicePort.save(restMapper.toArtist(request)));
        return ResponseEntity
                .created(URI.create("/artists/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public ArtistResponse update(@Valid @RequestBody ArtistCreateRequest request, @PathVariable Long id) {
        return restMapper.toArtistResponse(
                servicePort.update(id, restMapper.toArtist(request)));
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
