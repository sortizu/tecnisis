package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EconomicEvaluationCreateRequest {

    @NotNull(message = "salesPrice is required")
    @Min(value = 0, message = "salesPrice must be greater than or equal to 0")
    private BigDecimal salesPrice;

    @NotNull(message = "galleryPercentage is required")
    @Min(value = 0, message = "galleryPercentage must be greater than or equal to 0")
    @Max(value = 100, message = "galleryPercentage must be less than or equal to 100")
    private BigDecimal galleryPercentage;

    private LocalDate evaluationDate;


    private String status;

    @NotNull(message = "specialist is required")
    private Long specialistId;

    @NotNull(message = "request is required")
    private Long requestId;

    @NotNull(message = "document is required")
    private Long documentId;
}