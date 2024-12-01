package com.unmsm.movil.tecnisis.art_galery.domain.exception;

import com.unmsm.movil.tecnisis.art_galery.utils.ErrorCatalog;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
        super(ErrorCatalog.INVALID_CREDENTIALS.getMessage());
    }
}
