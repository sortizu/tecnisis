package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtistUpdateRequest {

    @NotNull(message = "person is required")
    private PersonUpdateRequest person;
}