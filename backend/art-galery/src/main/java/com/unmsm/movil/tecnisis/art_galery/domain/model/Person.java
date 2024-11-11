package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Person {
    private Long id;
    private String name;
    private String dni;
    private String address;
    private String gender;
    private String phone;
    private String role;
}
