package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.*;

import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RequestResponse {
    private Long id;
    private LocalDate date;
    private String status;
    private ArtWorkResponse artWork;
}
