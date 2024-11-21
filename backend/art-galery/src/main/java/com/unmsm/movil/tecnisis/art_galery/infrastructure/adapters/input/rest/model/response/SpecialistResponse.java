package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistResponse {
    private Long id;
    private Boolean isAvailable;
    private PersonResponse person;
}
