package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtWorkServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtWorkPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.TechniquePersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtWorkNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.TechniqueNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ArtWork;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Technique;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class ArtWorkService implements ArtWorkServicePort {

    private final ArtWorkPersistencePort  artWorkPersistencePort;
    private final ArtistPersistencePort  artistPersistencePort;
    private final TechniquePersistencePort   techniquePersistencePort;

    @Override
    public ArtWork findById(Long id) {
        return artWorkPersistencePort
                .findById(id)
                .orElseThrow(ArtWorkNotFoundException::new);
    }

    @Override
    public List<ArtWork> findAll() {
        return artWorkPersistencePort.findAll();
    }

    @Override
    public ArtWork save(ArtWork artWork) {
        return artistPersistencePort.findById(artWork.getArtist().getId())
                .map(artist -> techniquePersistencePort.findById(artWork.getTechnique().getId())
                        .map(technique -> {
                            artWork.setArtist(artist);
                            artWork.setTechnique(technique);
                            return artWorkPersistencePort.save(artWork);
                        })
                        .orElseThrow(TechniqueNotFoundException::new)
                )
                .orElseThrow(ArtistNotFoundException::new);
    }

    @Override
    public ArtWork update(Long id, ArtWork request) {
        ArtWork artWork = artWorkPersistencePort.findById(id)
                .orElseThrow(ArtWorkNotFoundException::new);

        Artist artist = artistPersistencePort.findById(request.getArtist().getId())
                .orElseThrow(ArtistNotFoundException::new);

        Technique technique = techniquePersistencePort.findById(request.getTechnique().getId())
                .orElseThrow(TechniqueNotFoundException::new);

        artWork.setTitle(request.getTitle());
        artWork.setImage(request.getImage());
        artWork.setHeight(request.getHeight());
        artWork.setWidth(request.getWidth());
        artWork.setArtist(artist);
        artWork.setTechnique(technique);
        return artWorkPersistencePort.save(artWork);
    }

    @Override
    public void delete(Long id) {
        if (artWorkPersistencePort.findById(id).isEmpty()) throw new ArtWorkNotFoundException();
        artWorkPersistencePort.deleteById(id);
    }
}
