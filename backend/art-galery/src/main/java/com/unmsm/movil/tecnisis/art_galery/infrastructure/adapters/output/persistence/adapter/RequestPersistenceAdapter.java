package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.RequestPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.RequestPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// implements the output port of the application
@Component
@RequiredArgsConstructor
public class RequestPersistenceAdapter implements RequestPersistencePort {

    private final RequestRepository requestRepository;
    private final RequestPersistenceMapper requestMapper;

    @Override
    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id).map(requestMapper::toRequest);
    }

    @Override
    public List<Request> findAll() {
        return requestMapper.toRequestList(requestRepository.findAll());
    }

    @Override
    public Request save(Request request) {
        return requestMapper.toRequest(
                requestRepository.save(requestMapper.toRequestEntity(request)));
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }
}
