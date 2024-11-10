package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class PersonCreateRequest {
    @NotEmpty(message = "Name is required")
    @Max(value = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotEmpty(message = "DNI is required")
    @Max(value = 12, message = "DNI must be less than 12 characters")
    @Min(value = 8, message = "DNI must be more than 8 characters")
    private String dni;

    @NotEmpty(message = "Address is required")
    @Max(value = 255, message = "Address must be less than 255 characters")
    private String address;

    @NotEmpty(message = "Gender is required")
    @Max(value = 1, message = "Gender must be less than 1 characters")
    @Pattern(regexp = "[MF]", message = "Gender must be M or F")
    private String gender;

    @NotEmpty(message = "Phone is required")
    @Max(value = 9, message = "Phone must be less than 9 characters")
    @Min(value = 9, message = "Phone must be more than 9 characters")
    private String phone;
}
