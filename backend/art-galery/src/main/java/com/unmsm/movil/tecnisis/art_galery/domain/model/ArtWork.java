package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtWork {
    private Long id;
    private String title;
    private LocalDate creationDate;
    private String description;
    private String image;
    private BigDecimal height;
    private BigDecimal width;
    private Technique technique;
    private Artist artist;
}
