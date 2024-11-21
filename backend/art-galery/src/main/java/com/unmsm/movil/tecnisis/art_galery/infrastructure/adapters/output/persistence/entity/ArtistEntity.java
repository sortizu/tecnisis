package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "artists")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_person",  nullable = false)
    private PersonEntity person;
}
