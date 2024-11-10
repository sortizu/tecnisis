package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder @Getter @Setter
public class ArtWork {
    private Long id;
    private String title;
    private LocalDate creationDate;
    private String description;
    private String image;
    private Double height;
    private Double width;
    private Technique technique;
    private Artist artist;
}
