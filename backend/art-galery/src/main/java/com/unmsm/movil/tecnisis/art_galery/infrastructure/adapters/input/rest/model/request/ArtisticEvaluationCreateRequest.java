package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtisticEvaluationCreateRequest  {

    private LocalDate evaluationDate;

    @NotNull(message = "rating is required")
    @Min(value = 0, message = "rating must be greater than or equal to 0")
    @Max(value = 10, message = "rating must be less than or equal to 10")
    private BigDecimal rating;

    private Long documentId;
}
