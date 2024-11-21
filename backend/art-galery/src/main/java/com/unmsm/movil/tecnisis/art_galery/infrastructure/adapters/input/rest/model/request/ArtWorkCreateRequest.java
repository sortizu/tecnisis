package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtWorkCreateRequest {

    @NotBlank(message = "title is required")
    @Size(min = 3, max = 255, message = "title must be between 3 and 255 characters")
    private String title;

    @NotBlank(message = "image is required")
    private String image;

    @NotNull(message = "height is required")
    private BigDecimal height;

    @NotNull(message = "width is required")
    private BigDecimal width;

    @NotNull(message = "artist is required")
    private Long artistId;

    @NotNull(message = "technique is required")
    private Long techniqueId;
}
