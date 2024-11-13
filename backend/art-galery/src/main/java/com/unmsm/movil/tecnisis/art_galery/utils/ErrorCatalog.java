package com.unmsm.movil.tecnisis.art_galery.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    ARTIST_NOT_FOUND("ERR_ART_001", "Artist not found"),
    ARTIST_ALREADY_EXISTS("ERR_ART_002", "Artist already exists"),
    PERSON_NOT_FOUND("ERR_PER_001", "Person not found"),
    TECHNIQUE_NOT_FOUND("ERR_TEC_001", "Technique not found"),
    ARTWORK_NOT_FOUND("ERR_ART_003", "Artwork not found"),
    REQUEST_NOT_FOUND("ERR_REQ_001", "Request not found"),
    SPECIALIST_NOT_FOUND("ERR_SPE_001", "Specialist not found"),
    DOCUMENT_NOT_FOUND("ERR_DOC_001", "Document not found"),
    INVALID_PARAMETER("ERR_GEN_001", "Invalid parameter"),
    GENERIC_ERROR("ERR_GEN_002", "An unexpected error occurred");

    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
