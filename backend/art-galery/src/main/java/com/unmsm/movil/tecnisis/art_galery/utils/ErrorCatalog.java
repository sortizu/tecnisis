package com.unmsm.movil.tecnisis.art_galery.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

    // artist
    ARTIST_NOT_FOUND("ERR_ART_001", "Artist not found"),
    ARTIST_ALREADY_EXISTS("ERR_ART_002", "Artist already exists"),

    // person
    PERSON_NOT_FOUND("ERR_PER_001", "Person not found"),
    PERSON_ROLE_NOT_SUPPORTED("ERR_PER_002", "Person role not supported"),

    // technique
    TECHNIQUE_NOT_FOUND("ERR_TEC_001", "Technique not found"),

    // art work
    ARTWORK_NOT_FOUND("ERR_ART_003", "Artwork not found"),

    // request
    REQUEST_NOT_FOUND("ERR_REQ_001", "Request not found"),

    // specialist
    SPECIALIST_NOT_FOUND("ERR_SPE_001", "Specialist not found"),

    // document
    DOCUMENT_NOT_FOUND("ERR_DOC_001", "Document not found"),

    // artistic evaluation
    ARTISTIC_EVALUATION_NOT_FOUND("ERR_AVA_001", "Artistic evaluation not found"),

    // economic evaluation
    ECONOMIC_EVALUATION_NOT_FOUND("ERR_EVA_001", "Economic evaluation not found"),

    // user
    USER_NOT_FOUND("ERR_USR_001", "User not found"),

    // parameters
    INVALID_PARAMETER("ERR_GEN_001", "Invalid parameter"),

    // manager
    MANAGER_NOT_FOUND("ERR_MAN_001", "Manager not found"),

    // process
    PROCESS_NOT_READY("ERR_PRO_001", "Process not ready for completion"),

    // generic error
    GENERIC_ERROR("ERR_GEN_002", "An unexpected error occurred"),

    // credentials
    INVALID_CREDENTIALS("ERR_ACC_002", "Invalid credentials"),

    // login
    ACCOUNT_LOCKED("ERR_USR_002", "Account locked");


    private final String code;
    private final String message;

    ErrorCatalog(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
