package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtistCreateRequest {
    @NotNull(message = "person_id is required")
    private Long personId;
}
