package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "requests")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Long id;

    @Column(name = "request_date")
    private LocalDate date;

    private String status;

    @ManyToOne
    @JoinColumn(name = "id_artwork", nullable = false)
    private ArtWorkEntity artWork;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ArtisticEvaluationEntity> artisticEvaluations;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<EconomicEvaluationEntity> economicEvaluations;

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }
}