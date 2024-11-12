package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtWorkResponse {
    private Long id;
    private String title;
    private LocalDate creationDate;
    private String image;
    private BigDecimal height;
    private BigDecimal width;
    private ArtistResponse artist;
    private TechniqueResponse technique;
}
