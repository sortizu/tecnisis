package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
    private Long id;
    private String email;
    private String name;
    private String dni;
    private String address;
    private String gender;
    private String phone;
    private String role;
}
