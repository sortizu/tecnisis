package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.RequestServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtWorkPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.RequestPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtWorkNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.RequestNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class RequestService implements RequestServicePort {

    private final RequestPersistencePort requestPersistencePort;
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
    public Request save(Request request) {
        return artWorkPersistencePort
                .findById(request.getArtWork().getId())
                .map(artWork -> {
                    request.setArtWork(artWork);
                    return requestPersistencePort.save(request);
                })
                .orElseThrow(ArtWorkNotFoundException::new);
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
