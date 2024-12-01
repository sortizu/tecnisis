package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "artists")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_person",  nullable = false)
    private PersonEntity person;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ArtWorkEntity> artWorks;
}