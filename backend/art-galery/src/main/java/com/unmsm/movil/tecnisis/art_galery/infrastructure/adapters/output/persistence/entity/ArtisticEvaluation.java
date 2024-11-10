package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "artistic_evaluations")
public class ArtisticEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluation")
    private Long id;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    private String rating;

    private String result;

    @ManyToOne
    @JoinColumn(name = "id_specialist", nullable = false)
    private SpecialistEntity specialist;

    @ManyToOne
    @JoinColumn(name = "id_request", nullable = false)
    private RequestEntity request;

    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private DocumentEntity document;
}
