package com.unmsm.movil.tecnisis.art_galery.domain.exception;

import com.unmsm.movil.tecnisis.art_galery.utils.ErrorCatalog;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException() {
        super(ErrorCatalog.ACCOUNT_LOCKED.getMessage());
    }
}
