package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "artistic_evaluations")
public class ArtisticEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluation")
    private Long id;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    private BigDecimal rating;

    private String result;

    @ManyToOne
    @JoinColumn(name = "id_specialist", nullable = false)
    private SpecialistEntity specialist;

    @ManyToOne
    @JoinColumn(name = "id_request", referencedColumnName = "id_request", nullable = false)
    @JsonManagedReference
    private RequestEntity request;

    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private DocumentEntity document;

    @PrePersist
    public void prePersist() {
        this.evaluationDate = LocalDate.now();
    }
}