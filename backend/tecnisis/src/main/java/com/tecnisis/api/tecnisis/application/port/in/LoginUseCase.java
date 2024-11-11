package com.tecnisis.api.tecnisis.application.port.in;

import com.tecnisis.api.tecnisis.domain.model.AppUser;

public interface LoginUseCase {
    AppUser login(String email, String password);
}
