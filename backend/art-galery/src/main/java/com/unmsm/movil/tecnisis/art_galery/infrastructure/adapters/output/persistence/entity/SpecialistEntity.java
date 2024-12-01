package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "specialists")
public class SpecialistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_specialist")
    private Long id;

    @Column(name = "availability")
    private Boolean isAvailable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "specialist_techniques",
            joinColumns = @JoinColumn(name = "id_specialist"),
            inverseJoinColumns = @JoinColumn(name = "id_technique")
    )
    List<TechniqueEntity> techniques;

    @OneToOne
    @JoinColumn(name = "id_person",  nullable = false)
    private PersonEntity person;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ArtisticEvaluationEntity> artisticEvaluations;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<EconomicEvaluationEntity> economicEvaluations;
}