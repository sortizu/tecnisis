package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class RegisterRequest {
    @NotEmpty(message = "El correo electrónico es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String email;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;

    @NotEmpty(message = "El nombre es obligatorio")
    private String name;

    @JsonProperty("idNumber")
    @NotEmpty(message = "El número de identificación es obligatorio")
    private String idNumber;

    private String address;

    @NotEmpty(message = "El género es obligatorio")
    private String gender;

    @NotEmpty(message = "El teléfono es obligatorio")
    private String phone;

    @JsonProperty("userRole")
    private String userRole;

}