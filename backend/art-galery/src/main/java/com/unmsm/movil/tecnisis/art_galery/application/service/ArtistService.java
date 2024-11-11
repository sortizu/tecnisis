package com.unmsm.movil.tecnisis.art_galery.application.service;

import com.unmsm.movil.tecnisis.art_galery.application.ports.input.ArtistServicePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.ArtistPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.application.ports.output.PersonPersistencePort;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.PersonNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

// implements the input port and use the output port
@Service
@RequiredArgsConstructor
public class ArtistService implements ArtistServicePort {

    private final ArtistPersistencePort artistPersistencePort;
    private final PersonPersistencePort personPersistencePort;

    @Override
    public Artist findById(Long id) {
        return artistPersistencePort
                .findById(id)
                .orElseThrow(ArtistNotFoundException::new);
    }

    @Override
    public List<Artist> findAll() {
        return artistPersistencePort.findAll();
    }

    @Override
    public Artist save(Artist artist) {
        return personPersistencePort.findById(artist.getPerson().getId())
                .map(person -> {
                    artist.setPerson(person);
                    return artistPersistencePort.save(artist);
                })
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public Artist update(Long id, Artist artist) {
        if (artistPersistencePort.findById(id).isEmpty()) throw new ArtistNotFoundException();
        return personPersistencePort.findById(artist.getPerson().getId())
                .map(person -> {
                    artist.setPerson(person);
                    return artistPersistencePort.save(artist);
                })
                .orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if (artistPersistencePort.findById(id).isEmpty()) throw new ArtistNotFoundException();
        artistPersistencePort.deleteById(id);
    }
}
