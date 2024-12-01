package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.SpecialistServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.RequestRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.SpecialistRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.RequestResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.SpecialistResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.SpecialistUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specialists/v1/api")
@Tag(name = "Specialist Controller", description = "Endpoint to management specialists")
public class SpecialistRestAdapter {

    private final SpecialistServicePort servicePort;
    private final SpecialistRestMapper specialistMapper;
    private final RequestRestMapper requestMapper;

    @GetMapping
    public List<SpecialistResponse> findAll() {
        return specialistMapper.toSpecialistResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public SpecialistResponse findById(@PathVariable Long id) {
        return specialistMapper.toSpecialistResponse(servicePort.findById(id));
    }

    @GetMapping("/artisticRequests/{id}")
    public List<RequestResponse> findArtisticRequestsById(@PathVariable Long id) {
        return requestMapper
                .toRequestResponseList(
                        servicePort.findArtisticRequestsBySpecialistId(id)
                );
    }

    @GetMapping("/economicRequests/{id}")
    public List<RequestResponse> findEconomicRequestsById(@PathVariable Long id) {
        return requestMapper
                .toRequestResponseList(
                        servicePort.findEconomicRequestsBySpecialistId(id)
                );
    }

    @PutMapping("/{id}")
    public SpecialistResponse update(@Valid @RequestBody SpecialistUpdateRequest request, @PathVariable Long id) {
        return specialistMapper.toSpecialistResponse(
                servicePort.update(id, specialistMapper.toSpecialist(request))
        );
    }

    @PatchMapping("/{idSpecialist}/technique/{idTechnique}")
    public ResponseEntity<Void> addTechnique(@PathVariable Long idSpecialist,
                                             @PathVariable Long idTechnique) {
        servicePort.addTechnique(idSpecialist, idTechnique);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}