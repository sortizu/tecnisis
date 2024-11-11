package com.tecnisis.api.tecnisis.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "id_number", unique = true, nullable = false)
    private String idNumber;

    private String address;

    @Column(length = 1)
    private char gender;

    private String phone;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private AppUser appUser;
}
