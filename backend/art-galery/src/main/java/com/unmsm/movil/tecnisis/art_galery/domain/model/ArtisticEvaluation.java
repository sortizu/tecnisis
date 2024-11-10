package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Builder @Getter @Setter
public class ArtisticEvaluation {
    private Long id;
    private LocalDate evaluationDate;
    private Double rating;
    private String result;
    private Specialist specialist;
    private Request request;
    private Document document;
}
