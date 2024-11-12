package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.adapter;

import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtWorkPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.mapper.ArtWorkPersistenceMapper;
import com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.output.persistence.repository.ArtWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArtWorkPersistenceAdapter implements ArtWorkPersistencePort {

    private final ArtWorkRepository artWorkRepository;
    private final ArtWorkPersistenceMapper artWorkMapper;

    @Override
    public Optional<ArtWork> findById(Long id) {
        return artWorkRepository.findById(id).map(artWorkMapper::toArtWork);
    }

    @Override
    public List<ArtWork> findAll() {
        return artWorkMapper.toArtWorkList(artWorkRepository.findAll());
    }

    @Override
    public ArtWork save(ArtWork artWork) {
        return artWorkMapper.toArtWork(artWorkRepository.save(artWorkMapper.toArtWorkEntity(artWork)));
    }

    @Override
    public void deleteById(Long id) {
        artWorkRepository.deleteById(id);
    }
}
