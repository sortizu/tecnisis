package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtisticEvaluationServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.*;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtisticEvaluationNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.DocumentNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class ArtisticEvaluationService implements ArtisticEvaluationServicePort {

    private final ArtisticEvaluationPersistencePort artisticEvaluationPersistencePort;
    private final EconomicEvaluationPersistencePort economicEvaluationPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
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
    public ArtisticEvaluation update(Long id, ArtisticEvaluation artisticEvaluation) {
        ArtisticEvaluation evaluation = artisticEvaluationPersistencePort
                .findById(id)
                .orElseThrow(ArtisticEvaluationNotFoundException::new);

        // Manejar el documento relacionado
        Document document = Optional.ofNullable(artisticEvaluation.getDocument())
                .map(doc -> documentPersistencePort.findById(doc.getId())
                        .orElseThrow(DocumentNotFoundException::new))
                .orElse(null);
        evaluation.setDocument(document);

        // Actualizar campos
        evaluation.setEvaluationDate(artisticEvaluation.getEvaluationDate());
        evaluation.setRating(artisticEvaluation.getRating());
        evaluation.setResult(artisticEvaluation.getResult());

        // Validar si el rating es mayor que 0
        if (evaluation.getRating().compareTo(BigDecimal.ZERO) != 0 && !economicEvaluationExists(evaluation.getRequest())) {
            List<Specialist> specialists = specialistPersistencePort
                    .findByRole("ECONOMIC-EVALUATOR")
                    .stream()
                    .filter(Specialist::getIsAvailable)
                    .toList();
            handleEconomicEvaluation(evaluation, specialists);
        }

        return artisticEvaluationPersistencePort.save(evaluation);
    }

    @Override
    public ArtisticEvaluation findByRequestId(Long id) {
        return artisticEvaluationPersistencePort
                .findByRequestId(id)
                .orElseThrow(ArtisticEvaluationNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (artisticEvaluationPersistencePort.findById(id).isEmpty()) throw new ArtisticEvaluationNotFoundException();
        artisticEvaluationPersistencePort.deleteById(id);
    }

    private boolean economicEvaluationExists(Request request) {
        return economicEvaluationPersistencePort.findByRequestId(request.getId()).isPresent();
    }

    private void handleEconomicEvaluation(ArtisticEvaluation artisticEvaluation, List<Specialist> specialists) {
        EconomicEvaluation economicEvaluation;

        if (!specialists.isEmpty()) {
            // Seleccionar el primer especialista disponible
            Specialist specialist = specialists.get(0);

            // Crear una evaluación económica con el especialista asignado
            economicEvaluation = EconomicEvaluation.builder()
                    .evaluationDate(LocalDate.now())
                    .status("APPROVED")
                    .specialist(specialist)
                    .request(artisticEvaluation.getRequest())
                    .build();

            // Marcar al especialista como no disponible
            specialist.setIsAvailable(false);
            specialistPersistencePort.save(specialist);
        } else {
            // Crear una evaluación económica con estado PENDING y sin especialista asignado
            economicEvaluation = EconomicEvaluation.builder()
                    .evaluationDate(LocalDate.now())
                    .status("PENDING")
                    .specialist(null)
                    .request(artisticEvaluation.getRequest())
                    .build();
        }

        // Guardar la evaluación económica
        economicEvaluationPersistencePort.save(economicEvaluation);
    }

}
