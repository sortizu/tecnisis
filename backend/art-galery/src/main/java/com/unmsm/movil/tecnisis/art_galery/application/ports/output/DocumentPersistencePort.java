package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentPersistencePort {
    Optional<Document> findById(Long id);
    List<Document> findAll();
    Document save(Document document);
    void deleteById(Long id);
}
