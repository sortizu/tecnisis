package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtisticEvaluationResponse {
    private Long id;
    private LocalDate evaluationDate;
    private BigDecimal rating;
    private String result;
    private String status;
    private SpecialistResponse specialist;
    private RequestResponse request;
    private DocumentResponse document;
}
