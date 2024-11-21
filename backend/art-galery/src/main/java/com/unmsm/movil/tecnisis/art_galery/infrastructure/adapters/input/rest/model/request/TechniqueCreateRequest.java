package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TechniqueCreateRequest {
    @NotEmpty(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotEmpty(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
}
