package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "artworks")
public class ArtWorkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artwork")
    private Long id;

    private String title;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "image_address")
    private String image;

    private BigDecimal height;
    private BigDecimal width;

    @ManyToOne
    @JoinColumn(name = "id_artist", referencedColumnName = "id_artist", nullable = false)
    @JsonManagedReference
    private ArtistEntity artist;

    @ManyToOne
    @JoinColumn(name = "id_technique", nullable = false)
    private TechniqueEntity technique;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDate.now();
    }
}
