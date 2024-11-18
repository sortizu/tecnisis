package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String name;
    private String dni;
    private String address;
    private String gender;
    private String phone;
    private String role;
}
