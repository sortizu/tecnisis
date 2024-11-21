package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity.DocumentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentPersistenceMapper {
    DocumentEntity toDocumentEntity(Document document);
    Document toDocument(DocumentEntity documentEntity);
    List<Document> toDocumentList(List<DocumentEntity> documentEntityList);
}
