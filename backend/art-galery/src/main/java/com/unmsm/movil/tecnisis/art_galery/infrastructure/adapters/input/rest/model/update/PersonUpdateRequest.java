package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PersonUpdateRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @Pattern(regexp = "\\d{9}", message = "Phone must be a valid 9-digit number")
    private String phone;
}