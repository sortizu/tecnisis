package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Request {
    private Long id;
    private LocalDate date;
    private String status;
    private ArtWork artWork;
}

