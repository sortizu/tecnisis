package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class ArtistResponse {
    private Long id;
    private PersonResponse person;
}
