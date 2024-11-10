package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class Person {
    private Long id;
    private String name;
    private String dni;
    private String address;
    private Character gender;
    private String phone;
    private String userRole;
    private  User user;
}
