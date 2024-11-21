package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DocumentCreateRequest {
    @NotBlank(message = "document path is required")
    @Size(max = 255, message = "document path must be less than 255 characters")
    private String path;
}
