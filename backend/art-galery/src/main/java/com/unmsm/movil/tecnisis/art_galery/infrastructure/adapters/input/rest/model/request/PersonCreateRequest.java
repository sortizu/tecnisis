package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PersonCreateRequest {
    @NotEmpty(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotEmpty(message = "DNI is required")
    @Size(min = 8, max = 12, message = "DNI must be between 8 and 12 characters")
    private String dni;

    @NotEmpty(message = "Address is required")
    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @NotEmpty(message = "Gender is required")
    @Size(max = 1, message = "Gender must be 1 character")
    @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
    private String gender;

    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "\\d{9}", message = "Phone must be a valid 9-digit number")
    private String phone;

    @NotEmpty(message = "Rol is required")
    @Size(max = 255, message = "Rol must be less than 255 characters")
    private String role;
}
