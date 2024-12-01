package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.EconomicEvaluationServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.EconomicEvaluationRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.EconomicEvaluationCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.EconomicEvaluationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/economic-evaluations/v1/api")
@Tag(name = "Economic Evaluation Controller", description = "Endpoint to management economic evaluations")
public class EconomicEvaluationRestAdapter {

    private final EconomicEvaluationServicePort servicePort;
    private final EconomicEvaluationRestMapper restMapper;

    @GetMapping
    public List<EconomicEvaluationResponse> findAll() {
        return restMapper.toEconomicEvaluationResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public EconomicEvaluationResponse findById(@PathVariable Long id) {
        return restMapper.toEconomicEvaluationResponse(
                servicePort.findById(id));
    }

    @GetMapping("/request/{id}")
    public EconomicEvaluationResponse findByRequestId(@PathVariable Long id) {
        return restMapper.toEconomicEvaluationResponse(
                servicePort.findByRequestId(id)
        );
    }

    @PutMapping("/{id}")
    public EconomicEvaluationResponse update(@Valid @RequestBody EconomicEvaluationCreateRequest request, @PathVariable Long id) {
        return restMapper.toEconomicEvaluationResponse(
                servicePort.update(id, restMapper.toEconomicEvaluation(request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
