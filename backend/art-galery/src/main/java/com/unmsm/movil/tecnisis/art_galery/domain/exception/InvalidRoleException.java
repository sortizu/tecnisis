package com.unmsm.movil.tecnisis.art_galery.domain.exception;

import com.unmsm.movil.tecnisis.art_galery.utils.ErrorCatalog;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(ErrorCatalog.INVALID_ROLE.getMessage());
    }
}
