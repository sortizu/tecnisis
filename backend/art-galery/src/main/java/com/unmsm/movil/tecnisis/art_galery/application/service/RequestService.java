package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.RequestServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.*;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtWorkNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ProcessNotReadyForCompletionException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.RequestNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class RequestService implements RequestServicePort {

    private final RequestPersistencePort requestPersistencePort;
    private final ArtisticEvaluationPersistencePort artisticEvaluationPersistencePort;
    private final EconomicEvaluationPersistencePort economicEvaluationPersistencePort;
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
    public ArtisticEvaluation findArtisticEvaluationByRequestId(Long requestId) {
        return artisticEvaluationPersistencePort
                .findByRequestId(requestId)
                .orElseThrow(RequestNotFoundException::new);
    }

    @Override
    public Request save(Request request) {
        // Obtener la obra de arte
        ArtWork artWork = artWorkPersistencePort
                .findById(request.getArtWork().getId())
                .orElseThrow(ArtWorkNotFoundException::new);
        request.setArtWork(artWork);
        request.setStatus("PENDING");

        // Obtener el especialista
        List<Specialist> specialists = specialistPersistencePort
                .findByRole("ART-EVALUATOR")
                .stream()
                .filter(s -> {
                    if (s.getTechniques().isEmpty()) return false;
                    List<String> techniques = s.getTechniques().stream().map(Technique::getName).toList();
                    return techniques.contains(artWork.getTechnique().getName());
                })
                .toList();

        if (specialists.isEmpty()) {
            return requestPersistencePort.save(request);
        }

        Specialist specialist = specialists.get(0);

        // Guardar el request
        Request requestToSave = requestPersistencePort.save(request);

        // Reanexar el request al contexto de persistencia
        Request managedRequest = requestPersistencePort.findById(requestToSave.getId())
                .orElseThrow(RequestNotFoundException::new);

        // Crear la evaluación artística
        ArtisticEvaluation artisticEvaluation = ArtisticEvaluation
                .builder()
                .rating(BigDecimal.ZERO)
                .status("PENDING")
                .specialist(specialist)
                .request(managedRequest) // Usar el request gestionado
                .build();

        // Guardar la evaluación artística
        artisticEvaluationPersistencePort.save(artisticEvaluation);

        // set availability false in artist specialist
        specialist.setIsAvailable(false);
        specialistPersistencePort.save(specialist);

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

    @Override
    @Transactional
    public void completeProcess(Long requestId) {
        Request request = requestPersistencePort.findById(requestId)
                .orElseThrow(RequestNotFoundException::new);

        // Validar requisitos
        validateProcessCompletion(request);

        // Actualizar resultado de la evaluación artística
        updateArtisticEvaluationResult(request);

        // Restablecer disponibilidad de especialistas
        resetSpecialistsAvailability(request);

        // Procesar registros pendientes
        processPendingRequests();
        processPendingEconomicEvaluations();
    }

    private void validateProcessCompletion(Request request) {
        boolean isArtisticEvaluationApproved = artisticEvaluationPersistencePort
                .findByRequestId(request.getId())
                .map(evaluation -> "APPROVED".equals(evaluation.getStatus()))
                .orElse(false);

        boolean isEconomicEvaluationApproved = economicEvaluationPersistencePort
                .findByRequestId(request.getId())
                .map(evaluation -> "APPROVED".equals(evaluation.getStatus()))
                .orElse(false);

        if (!"APPROVED".equals(request.getStatus()) || !isArtisticEvaluationApproved || !isEconomicEvaluationApproved) {
            throw new ProcessNotReadyForCompletionException();
        }
    }

    private void updateArtisticEvaluationResult(Request request) {
        artisticEvaluationPersistencePort.findByRequestId(request.getId())
                .ifPresent(evaluation -> {
                    evaluation.setResult("APPROVED");
                    artisticEvaluationPersistencePort.save(evaluation);
                });
    }

    private void resetSpecialistsAvailability(Request request) {
        artisticEvaluationPersistencePort.findByRequestId(request.getId())
                .map(ArtisticEvaluation::getSpecialist)
                .ifPresent(specialist -> {
                    specialist.setIsAvailable(true);
                    specialistPersistencePort.save(specialist);
                });

        economicEvaluationPersistencePort.findByRequestId(request.getId())
                .map(EconomicEvaluation::getSpecialist)
                .ifPresent(specialist -> {
                    specialist.setIsAvailable(true);
                    specialistPersistencePort.save(specialist);
                });
    }

    private void processPendingRequests() {
        List<Request> pendingRequests = requestPersistencePort.findByStatus("PENDING");
        if (!pendingRequests.isEmpty()) {
            save(pendingRequests.get(0)); // Reutilizar el Flujo 01
        }
    }

    private void processPendingEconomicEvaluations() {
        List<EconomicEvaluation> pendingEconomicEvaluations = economicEvaluationPersistencePort.findByStatus("PENDING");
        if (!pendingEconomicEvaluations.isEmpty()) {
            EconomicEvaluation pendingEvaluation = pendingEconomicEvaluations.get(0);

            if (pendingEvaluation.getId() == null) {
                throw new IllegalStateException("EconomicEvaluation ID must not be null for update");
            }

            List<Specialist> specialists = specialistPersistencePort
                    .findByRole("ECONOMIC-EVALUATOR")
                    .stream()
                    .filter(Specialist::getIsAvailable)
                    .toList();

            if (!specialists.isEmpty()) {
                Specialist specialist = specialists.get(0);
                pendingEvaluation.setSpecialist(specialist);
                pendingEvaluation.setStatus("APPROVED");
                specialist.setIsAvailable(false);
                specialistPersistencePort.save(specialist);
                economicEvaluationPersistencePort.save(pendingEvaluation);
            }
        }
    }
}