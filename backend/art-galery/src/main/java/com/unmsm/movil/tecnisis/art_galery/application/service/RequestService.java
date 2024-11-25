package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.RequestServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtWorkPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtisticEvaluationPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.RequestPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.SpecialistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtWorkNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.RequestNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtisticEvaluation;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Specialist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class RequestService implements RequestServicePort {

    private final RequestPersistencePort requestPersistencePort;
    private final ArtisticEvaluationPersistencePort artisticEvaluationPersistencePort;
    private final SpecialistPersistencePort specialistPersistencePort;
    private final ArtWorkPersistencePort artWorkPersistencePort;

    @Override
    public Request findById(Long id) {
        return requestPersistencePort
                .findById(id)
                .orElseThrow(RequestNotFoundException::new);
    }

    @Override
    public List<Request> findAll() {
        return requestPersistencePort
                .findAll();
    }

    @Override
    @Transactional
    public Request save(Request request) {
        // Obtener la obra de arte
        ArtWork artWork = artWorkPersistencePort
                .findById(request.getArtWork().getId())
                .orElseThrow(ArtWorkNotFoundException::new);
        request.setArtWork(artWork);

        // Obtener el especialista
        List<Specialist> specialists = specialistPersistencePort.findByRole("ART-EVALUATOR");
        if (specialists == null || specialists.isEmpty()) {
            throw new RuntimeException("No specialist found with role ART-EVALUATOR");
        }
        Specialist specialist = specialists.get(0);

        // Guardar el request
        Request requestToSave = requestPersistencePort.save(request);

        // Reanexar el request al contexto de persistencia
        Request managedRequest = requestPersistencePort.findById(requestToSave.getId())
                .orElseThrow(() -> new RuntimeException("Request not found after save"));

        // Crear la evaluación artística
        ArtisticEvaluation artisticEvaluation = ArtisticEvaluation
                .builder()
                .evaluationDate(LocalDate.now())
                .result("A")
                .specialist(specialist)
                .request(managedRequest) // Usar el request gestionado
                .build();

        // Guardar la evaluación artística
        artisticEvaluationPersistencePort.save(artisticEvaluation);

        return requestToSave;
    }

    @Override
    public Request update(Long id, Request request) {
        return requestPersistencePort
                .findById(id)
                .map(requestToUpdate -> artWorkPersistencePort
                        .findById(request.getArtWork().getId())
                        .map(artWork -> {
                            requestToUpdate.setStatus(request.getStatus());
                            requestToUpdate.setArtWork(artWork);
                            return requestPersistencePort.save(requestToUpdate);
                        })
                        .orElseThrow(ArtWorkNotFoundException::new)
                )
                .orElseThrow(RequestNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (requestPersistencePort.findById(id).isEmpty()) throw new RequestNotFoundException();
        requestPersistencePort.deleteById(id);
    }
}
