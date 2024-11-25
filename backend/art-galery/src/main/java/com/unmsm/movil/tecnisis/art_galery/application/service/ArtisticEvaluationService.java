package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtisticEvaluationServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtisticEvaluationPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.DocumentPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.RequestPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtisticEvaluationNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.DocumentNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.RequestNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.SpecialistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Document;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class ArtisticEvaluationService implements ArtisticEvaluationServicePort {

    private final ArtisticEvaluationPersistencePort artisticEvaluationPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
    private final RequestPersistencePort requestPersistencePort;
    private final DocumentPersistencePort  documentPersistencePort;

    @Override
    public ArtisticEvaluation findById(Long id) {
        return artisticEvaluationPersistencePort
                .findById(id)
                .orElseThrow(ArtisticEvaluationNotFundException::new);
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
                .orElseThrow(ArtisticEvaluationNotFundException::new);

        Specialist specialist = specialistPersistencePort
                .findById(artisticEvaluation.getSpecialist().getId())
                .orElseThrow(SpecialistNotFoundException::new);

        Request request = requestPersistencePort
                .findById(artisticEvaluation.getRequest().getId())
                .orElseThrow(RequestNotFoundException::new);

        Document document = documentPersistencePort
                .findById(artisticEvaluation.getDocument().getId())
                .orElseThrow(DocumentNotFoundException::new);

        evaluation.setRating(artisticEvaluation.getRating());
        evaluation.setResult(artisticEvaluation.getResult());
        evaluation.setSpecialist(specialist);
        evaluation.setRequest(request);
        evaluation.setDocument(document);
        return artisticEvaluationPersistencePort.save(evaluation);
    }

    @Override
    public void delete(Long id) {
        if (artisticEvaluationPersistencePort.findById(id).isEmpty()) throw new ArtisticEvaluationNotFundException();
        artisticEvaluationPersistencePort.deleteById(id);
    }
}
