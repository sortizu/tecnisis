package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "requests")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Long id;

    @Column(name = "request_date")
    private LocalDate requestDate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "id_artwork", nullable = false)
    private ArtWorkEntity artWork;
}
