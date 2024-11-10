package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "persons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long id;

    private String name;

    @Column(name = "id_number")
    private String dni;

    private String address;

    private Character gender;

    private String phone;

    @Column(name = "user_role")
    private String role;
}
