package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EconomicEvaluation {
    private Long id;
    private LocalDate evaluationDate;
    private BigDecimal salePrice;
    private BigDecimal galleryPercentage;
    private Specialist specialist;
    private Request request;
    private Document document;
}
