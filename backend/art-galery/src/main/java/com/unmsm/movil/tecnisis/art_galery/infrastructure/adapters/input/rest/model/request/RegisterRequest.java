package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Name is required")
    private String name;

    @JsonProperty("idNumber")
    @NotEmpty(message = "Identification number is required")
    private String idNumber;

    private String address;

    @NotEmpty(message = "Gender is required")
    private String gender;

    @NotEmpty(message = "Phone number is required")
    private String phone;

    @JsonProperty("userRole")
    private String userRole;
}
