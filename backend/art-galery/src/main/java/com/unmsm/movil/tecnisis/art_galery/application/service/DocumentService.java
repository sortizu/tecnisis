package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.DocumentServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.DocumentPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.DocumentNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class DocumentService implements DocumentServicePort {

    private final DocumentPersistencePort  documentPersistencePort;

    @Override
    public Document findById(Long id) {
        return documentPersistencePort
                .findById(id)
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public List<Document> findAll() {
        return documentPersistencePort.findAll();
    }

    @Override
    public Document save(Document document) {
        return documentPersistencePort.save(document);
    }

    @Override
    public Document update(Long id, Document request) {
        return documentPersistencePort
                .findById(id)
                .map(document -> {
                    document.setPath(request.getPath());
                    return documentPersistencePort.save(document);
                })
                .orElseThrow(DocumentNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (documentPersistencePort.findById(id).isEmpty()) throw new DocumentNotFoundException();
        documentPersistencePort.deleteById(id);
    }
}
