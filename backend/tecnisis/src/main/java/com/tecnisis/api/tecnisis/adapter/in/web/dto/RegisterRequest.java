package com.tecnisis.api.tecnisis.adapter.in.web.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String idNumber;
    private String address;
    private String gender;
    private String phone;
    private String userRole;  // Podríamos asignarle un rol por defecto, como "USER"
}
