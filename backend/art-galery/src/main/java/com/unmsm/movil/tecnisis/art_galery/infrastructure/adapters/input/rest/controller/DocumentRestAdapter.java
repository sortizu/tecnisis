package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.DocumentServicePort;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper.DocumentRestMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.DocumentCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.DocumentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
@Tag(name = "Document Controller", description = "Endpoint to management documents")
public class DocumentRestAdapter {
    private final DocumentServicePort servicePort;
    private final DocumentRestMapper restMapper;

    @GetMapping("/v1/api")
    public List<DocumentResponse> findAll() {
        return restMapper.toDocumentResponseList(servicePort.findAll());
    }

    @GetMapping("/v1/api/{id}")
    public DocumentResponse findById(@PathVariable Long id) {
        return restMapper.toDocumentResponse(servicePort.findById(id));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<DocumentResponse> save(@Valid @RequestBody DocumentCreateRequest request) {
        DocumentResponse response = restMapper.toDocumentResponse(
                servicePort.save(restMapper.toDocument(request)));
        return ResponseEntity
                .created(URI.create("/documents/v1/api/" + response.getId()))
                .body(response);
    }

    @PutMapping("/v1/api/{id}")
    public DocumentResponse update(@Valid @RequestBody DocumentCreateRequest request, @PathVariable Long id) {
        return restMapper.toDocumentResponse(
                servicePort.update(id, restMapper.toDocument(request))
        );
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
