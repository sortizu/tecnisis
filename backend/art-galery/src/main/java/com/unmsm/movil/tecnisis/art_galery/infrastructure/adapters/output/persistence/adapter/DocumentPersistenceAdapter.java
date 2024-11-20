package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.DocumentPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.DocumentPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocumentPersistenceAdapter implements DocumentPersistencePort {

    private final DocumentRepository documentRepository;
    private final DocumentPersistenceMapper documentMapper;

    @Override
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id).map(documentMapper::toDocument);
    }

    @Override
    public List<Document> findAll() {
        return documentMapper.toDocumentList(documentRepository.findAll());
    }

    @Override
    public Document save(Document document) {
        return documentMapper.toDocument(
                documentRepository.save(
                        documentMapper.toDocumentEntity(document)));
    }

    @Override
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }
}
