package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SpecialistUpdateRequest {
    @NotNull(message = "person_id is required")
    private Boolean isAvailable;

    @NotNull(message = "person is required")
    private PersonUpdateRequest person;
}
