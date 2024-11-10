package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class PersonResponse {
    private Long id;
    private String name;
    private String dni;
    private String address;
    private String gender;
    private String phone;
}
