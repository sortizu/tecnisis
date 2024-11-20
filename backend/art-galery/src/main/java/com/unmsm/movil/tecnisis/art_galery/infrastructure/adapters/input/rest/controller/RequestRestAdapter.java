package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.RequestServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.RequestRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.RequestCreateRequest;
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
@RequestMapping("/requests")
@Tag(name = "Request Controller", description = "Endpoint to management requests")
public class RequestRestAdapter {

    private final RequestServicePort servicePort;
    private final RequestRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<RequestResponse> findAll() {
        return restMapper.toRequestResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public RequestResponse findById(@PathVariable Long id) {
        return restMapper.toRequestResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<RequestResponse> save(@Valid @RequestBody RequestCreateRequest request) {
        RequestResponse response = restMapper.toRequestResponse(
                servicePort.save(restMapper.toRequest(request)));
        return ResponseEntity
                .created(URI.create("/requests/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public RequestResponse update(@Valid @RequestBody RequestCreateRequest request, @PathVariable Long id) {
        return restMapper.toRequestResponse(
                servicePort.update(id, restMapper.toRequest(request))
        );
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
