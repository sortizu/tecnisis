package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.EconomicEvaluationServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.DocumentPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.EconomicEvaluationPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.RequestPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.*;
import com.unmsm.movil.tecnisis.art_galery.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EconomicEvaluationService implements EconomicEvaluationServicePort {

    private final EconomicEvaluationPersistencePort economicEvaluationPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
    private final RequestPersistencePort  requestPersistencePort;
    private final DocumentPersistencePort documentPersistencePort;

    @Override
    public EconomicEvaluation findById(Long id) {
        return economicEvaluationPersistencePort
                .findById(id)
                .orElseThrow(EconomicEvaluationNotFoundException::new);
    }

    @Override
    public List<EconomicEvaluation> findAll() {
        return economicEvaluationPersistencePort.findAll();
    }

    @Override
    public EconomicEvaluation save(EconomicEvaluation economicEvaluation) {
        Specialist specialist = specialistPersistencePort
                .findById(economicEvaluation.getSpecialist().getId())
                .orElseThrow(SpecialistNotFoundException::new);

        Request request = requestPersistencePort
                .findById(economicEvaluation.getRequest().getId())
                .orElseThrow(RequestNotFoundException::new);

        Document document = documentPersistencePort
                .findById(economicEvaluation.getDocument().getId())
                .orElseThrow(DocumentNotFoundException::new);

        economicEvaluation.setSpecialist(specialist);
        economicEvaluation.setRequest(request);
        economicEvaluation.setDocument(document);
        return economicEvaluationPersistencePort.save(economicEvaluation);
    }

    @Override
    public EconomicEvaluation update(Long id, EconomicEvaluation economicEvaluation) {

        EconomicEvaluation evaluation = economicEvaluationPersistencePort
                .findById(id)
                .orElseThrow(EconomicEvaluationNotFoundException::new);

        Specialist specialist = specialistPersistencePort
                .findById(economicEvaluation.getSpecialist().getId())
                .orElseThrow(SpecialistNotFoundException::new);

        Request request = requestPersistencePort
                .findById(economicEvaluation.getRequest().getId())
                .orElseThrow(RequestNotFoundException::new);

        Document document = documentPersistencePort
                .findById(economicEvaluation.getDocument().getId())
                .orElseThrow(DocumentNotFoundException::new);

        evaluation.setEvaluationDate(economicEvaluation.getEvaluationDate());
        evaluation.setGalleryPercentage(economicEvaluation.getGalleryPercentage());
        evaluation.setSalesPrice(economicEvaluation.getSalesPrice());
        evaluation.setSpecialist(specialist);
        evaluation.setRequest(request);
        evaluation.setDocument(document);
        return economicEvaluationPersistencePort.save(evaluation);
    }

    @Override
    public EconomicEvaluation findByRequestId(Long requestId) {
        return economicEvaluationPersistencePort
                .findByRequestId(requestId)
                .orElseThrow(EconomicEvaluationNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (economicEvaluationPersistencePort.findById(id).isEmpty()) throw new EconomicEvaluationNotFoundException();
        economicEvaluationPersistencePort.deleteById(id);
    }
}