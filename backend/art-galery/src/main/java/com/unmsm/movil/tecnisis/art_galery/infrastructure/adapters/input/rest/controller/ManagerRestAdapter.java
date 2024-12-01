package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ManagerServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.ManagerRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.ManagerResponse;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update.ManagerUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers/v1/api")
@Tag(name = "Manager Controller", description = "Endpoint to management managers")
public class ManagerRestAdapter {

    private final ManagerServicePort servicePort;
    private final ManagerRestMapper managerMapper;

    @GetMapping
    public List<ManagerResponse> findAll() {
        return managerMapper.toManagerResponseList(servicePort.findAll());
    }

    @GetMapping("/{id}")
    public ManagerResponse findById(@PathVariable Long id) {
        return managerMapper.toManagerResponse(servicePort.findById(id));
    }

    @PutMapping("/{id}")
    public ManagerResponse update(@Valid @RequestBody ManagerUpdateRequest request, @PathVariable Long id) {
        return managerMapper.toManagerResponse(
                servicePort.update(id, managerMapper.toManager(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}