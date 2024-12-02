package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PersonCreateRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "DNI is required")
    @Size(min = 8, max = 12, message = "DNI must be between 8 and 12 characters")
    private String dni;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @NotBlank(message = "Gender is required")
    @Size(max = 1, message = "Gender must be 1 character")
    @Pattern(regexp = "[MFX]", message = "Gender must be 'M' or 'F'")
    private String gender;

    @Pattern(regexp = "\\d{9}", message = "Phone must be a valid 9-digit number")
    private String phone;

    @NotEmpty(message = "Rol is required")
    @Pattern(regexp = "ARTIST|ART-EVALUATOR|ECONOMIC-EVALUATOR|MANAGER",  message = "Rol must be 'ARTIST', 'ART-EVALUATOR', 'ECONOMIC-EVALUATOR' or 'MANAGER'")
    @Size(max = 255, message = "Rol must be less than 255 characters")
    private String role;
}