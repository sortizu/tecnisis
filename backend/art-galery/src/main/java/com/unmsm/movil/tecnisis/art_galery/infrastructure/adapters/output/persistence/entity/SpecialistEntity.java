package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "specialists")
public class SpecialistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_specialist")
    private Long id;

    @Column(name = "availability")
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "id_person",  nullable = false)
    private PersonEntity person;


}
