package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtisticEvaluationServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ArtisticEvaluationRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.ArtisticEvaluationCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ArtisticEvaluationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artistic-evaluations/v1/api")
@Tag(name = "Artistic Evaluation Controller", description = "Endpoint to management artistic evaluations")
public class ArtisticEvaluationRestAdapter {

    private final ArtisticEvaluationServicePort servicePort;
    private final ArtisticEvaluationRestMapper restMapper;

    @GetMapping
    public List<ArtisticEvaluationResponse> findAll() {
        return restMapper.toArtisticEvaluationResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public ArtisticEvaluationResponse findById(@PathVariable Long id) {
        return restMapper.toArtisticEvaluationResponse(servicePort.findById(id));
    }

    @GetMapping("/request/{id}")
    public ArtisticEvaluationResponse findByRequestId(@PathVariable Long id) {
        return restMapper.toArtisticEvaluationResponse(
                servicePort.findByRequestId(id)
        );
    }

    @PutMapping("/{id}")
    public ArtisticEvaluationResponse update(@Valid @RequestBody ArtisticEvaluationCreateRequest request, @PathVariable Long id) {
        return restMapper.toArtisticEvaluationResponse(
                servicePort.update(id, restMapper.toArtisticEvaluation(request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}