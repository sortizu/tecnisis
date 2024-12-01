package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ArtistPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.RequestPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// implements the output port of application and use the repository
@Component
@RequiredArgsConstructor
public class ArtistPersistenceAdapter implements ArtistPersistencePort {

    private final ArtistRepository artistRepository;
    private final ArtistPersistenceMapper artistMapper;
    private final RequestPersistenceMapper requestMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id).map(artistMapper::toArtist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Artist> findAll() {
        return artistMapper.toArtistList(artistRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> findRequestsByArtistId(Long artistId) {
        return requestMapper.toRequestList(artistRepository.findRequestsById(artistId));
    }

    @Override
    @Transactional
    public Artist save(Artist artist) {
        return artistMapper.toArtist(artistRepository.save(artistMapper.toArtistEntity(artist)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        artistRepository.deleteById(id);
    }
}