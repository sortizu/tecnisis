package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder @Getter @Setter
public class Request {
    private Long id;
    private LocalDate requestDate;
    private String status;
    private ArtWork artWork;
}

