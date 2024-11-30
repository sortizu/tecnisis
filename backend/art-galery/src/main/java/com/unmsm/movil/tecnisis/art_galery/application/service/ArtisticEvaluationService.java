package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtisticEvaluationServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.*;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtisticEvaluationNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.DocumentNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.RequestNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.SpecialistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class ArtisticEvaluationService implements ArtisticEvaluationServicePort {

    private final ArtisticEvaluationPersistencePort artisticEvaluationPersistencePort;
    private final EconomicEvaluationPersistencePort economicEvaluationPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
    private final RequestPersistencePort requestPersistencePort;
    private final DocumentPersistencePort  documentPersistencePort;

    @Override
    public ArtisticEvaluation findById(Long id) {
        return artisticEvaluationPersistencePort
                .findById(id)
                .orElseThrow(ArtisticEvaluationNotFoundException::new);
    }

    @Override
    public List<ArtisticEvaluation> findAll() {
        return artisticEvaluationPersistencePort.findAll();
    }

    @Override
    public ArtisticEvaluation save(ArtisticEvaluation artisticEvaluation) {
        Specialist specialist = specialistPersistencePort
                .findById(artisticEvaluation.getSpecialist().getId())
                .orElseThrow(SpecialistNotFoundException::new);

        Request request = requestPersistencePort
                .findById(artisticEvaluation.getRequest().getId())
                .orElseThrow(RequestNotFoundException::new);

        Document document = documentPersistencePort
                .findById(artisticEvaluation.getDocument().getId())
                .orElseThrow(DocumentNotFoundException::new);

        artisticEvaluation.setSpecialist(specialist);
        artisticEvaluation.setRequest(request);
        artisticEvaluation.setDocument(document);
        return artisticEvaluationPersistencePort.save(artisticEvaluation);
    }

    @Override
    public ArtisticEvaluation update(Long id, ArtisticEvaluation artisticEvaluation) {
        ArtisticEvaluation evaluation = artisticEvaluationPersistencePort
                .findById(id)
                .orElseThrow(ArtisticEvaluationNotFoundException::new);

        Document document = null;

        if (artisticEvaluation.getDocument() != null){
            document = documentPersistencePort
                    .findById(artisticEvaluation.getDocument().getId())
                    .orElseThrow(DocumentNotFoundException::new);
        }

        evaluation.setRating(artisticEvaluation.getRating());
        evaluation.setEvaluationDate(artisticEvaluation.getEvaluationDate());
        evaluation.setResult(artisticEvaluation.getResult());
        evaluation.setDocument(document);

        if (evaluation.getRating().doubleValue() <= 0 || evaluation.isUpdated())
            return artisticEvaluationPersistencePort.save(evaluation);


        // Obtener el especialista
        List<Specialist> specialists = specialistPersistencePort
                .findByRole("ECONOMIC-EVALUATOR");

        if (specialists.isEmpty()) throw new SpecialistNotFoundException();

        Specialist specialist = specialists.get(0);

        EconomicEvaluation economicEvaluation = EconomicEvaluation.builder()
                .evaluationDate(LocalDate.now())
                .salesPrice(new BigDecimal("0"))
                .galleryPercentage(new BigDecimal("0"))
                .specialist(specialist)
                .request(evaluation.getRequest())
                .status("PENDING")
                .build();

        economicEvaluationPersistencePort.save(economicEvaluation);
        evaluation.setUpdated(true);
        return artisticEvaluationPersistencePort.save(evaluation);
    }

    @Override
    public void delete(Long id) {
        if (artisticEvaluationPersistencePort.findById(id).isEmpty()) throw new ArtisticEvaluationNotFoundException();
        artisticEvaluationPersistencePort.deleteById(id);
    }
}
