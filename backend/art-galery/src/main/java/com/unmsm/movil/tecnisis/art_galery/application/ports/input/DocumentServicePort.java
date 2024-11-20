package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;

import java.util.List;

public interface DocumentServicePort {
    Document findById(Long id);
    List<Document> findAll();
    Document save(Document document);
    Document update(Long id, Document document);
    void delete(Long id);
}
