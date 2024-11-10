package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtistServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class ArtistService implements ArtistServicePort {

    private final ArtistPersistencePort persistencePort;

    @Override
    public Artist findById(Long id) {
        return persistencePort
                .findById(id)
                .orElseThrow(ArtistNotFoundException::new);
    }

    @Override
    public List<Artist> findAll() {
        return persistencePort.findAll();
    }

    @Override
    public Artist save(Artist artist) {
        return persistencePort.save(artist);
    }

    @Override
    public Artist update(Long id, Artist artist) {
        return persistencePort.findById(id)
                .map(artistToUpdate -> {
                    artistToUpdate.setPerson(artist.getPerson());
                    return persistencePort.save(artistToUpdate);
                })
                .orElseThrow(ArtistNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (persistencePort.findById(id).isEmpty()) throw new ArtistNotFoundException();
        persistencePort.deleteById(id);
    }
}
