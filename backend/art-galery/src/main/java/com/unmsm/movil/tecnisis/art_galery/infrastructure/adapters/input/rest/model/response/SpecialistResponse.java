package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.*;

import java.util.List;

@Builder @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistResponse {
    private Long id;
    private Boolean isAvailable;
    private PersonResponse person;
    List<TechniqueResponse> techniques;
}