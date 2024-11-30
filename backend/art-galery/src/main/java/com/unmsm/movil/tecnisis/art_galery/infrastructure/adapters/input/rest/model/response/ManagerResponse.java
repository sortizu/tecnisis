package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class ManagerResponse {
    private Long id;
    private PersonResponse person;
}
