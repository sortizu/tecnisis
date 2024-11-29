package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "managers")
public class ManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_manager")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_person",  nullable = false)
    private PersonEntity person;
}
