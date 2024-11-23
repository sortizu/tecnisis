package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Getter @Setter
@Table(name = "economic_evaluations")
public class EconomicEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluation")
    private Long id;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    @Column(name = "sale_price")
    private BigDecimal salesPrice;

    @Column(name = "gallery_percentage")
    private BigDecimal galleryPercentage;

    @ManyToOne
    @JoinColumn(name = "id_specialist", nullable = false)
    private SpecialistEntity specialist;

    @ManyToOne
    @JoinColumn(name = "id_request", nullable = false)
    private RequestEntity request;

    @ManyToOne
    @JoinColumn(name = "id_document")
    private DocumentEntity document;

    @PrePersist
    public void prePersist() {
        this.evaluationDate = LocalDate.now();
    }
}