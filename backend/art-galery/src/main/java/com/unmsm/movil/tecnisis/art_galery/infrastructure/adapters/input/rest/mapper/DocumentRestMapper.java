package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.mapper;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request.DocumentCreateRequest;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response.DocumentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentRestMapper {
    Document toDocument(DocumentCreateRequest documentCreateRequest);
    DocumentResponse toDocumentResponse(Document document);
    List<DocumentResponse> toDocumentResponseList(List<Document> documentList);
}
