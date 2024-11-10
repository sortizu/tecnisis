package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder @Getter @Setter
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
