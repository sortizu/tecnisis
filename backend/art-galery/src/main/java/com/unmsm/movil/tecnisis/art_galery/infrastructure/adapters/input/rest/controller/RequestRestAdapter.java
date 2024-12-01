package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.RequestServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ArtisticEvaluationRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.RequestRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RequestCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtisticEvaluationResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.RequestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests/v1/api")
@Tag(name = "Request Controller", description = "Endpoint to management requests")
public class RequestRestAdapter {

    private final RequestServicePort servicePort;
    private final RequestRestMapper requestMapper;
    private final ArtisticEvaluationRestMapper artisticEvaluationMapper;

    @GetMapping
    public List<RequestResponse> findAll() {
        return requestMapper.toRequestResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public RequestResponse findById(@PathVariable Long id) {
        return requestMapper.toRequestResponse(servicePort.findById(id));
    }

    @GetMapping("/{id}/artistic-evaluation")
    public ArtisticEvaluationResponse findArtisticEvaluationByRequestId(@PathVariable Long id) {
        return artisticEvaluationMapper
                .toArtisticEvaluationResponse(
                        servicePort.findArtisticEvaluationByRequestId(id)
                );
    }

    @GetMapping("/{id}/complete-process")
    public ResponseEntity<Void> completeProcess(@PathVariable Long id) {
        servicePort.completeProcess(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<RequestResponse> save(@Valid @RequestBody RequestCreateRequest request) {
        RequestResponse response = requestMapper.toRequestResponse(
                servicePort.save(requestMapper.toRequest(request)));
        return ResponseEntity
                .created(URI.create("/requests/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public RequestResponse update(@Valid @RequestBody RequestCreateRequest request, @PathVariable Long id) {
        return requestMapper.toRequestResponse(
                servicePort.update(id, requestMapper.toRequest(request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}