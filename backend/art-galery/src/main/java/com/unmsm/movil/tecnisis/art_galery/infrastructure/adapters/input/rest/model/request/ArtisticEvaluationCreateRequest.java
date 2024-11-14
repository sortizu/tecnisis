package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.math.BigDecimal;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ArtisticEvaluationCreateRequest  {
    @NotNull(message = "rating is required")
    @Min(value = 0, message = "rating must be greater than or equal to 0")
    @Max(value = 10, message = "rating must be less than or equal to 10")
    private BigDecimal rating;

    @NotNull(message = "result is required")
    @Pattern(regexp = "[AR]", message = "result must be 'A' or 'R'")
    private String result;

    @NotNull(message = "specialist is required")
    private Long specialistId;

    @NotNull(message = "request is required")
    private Long requestId;

    @NotNull(message = "document is required")
    private Long documentId;
}
