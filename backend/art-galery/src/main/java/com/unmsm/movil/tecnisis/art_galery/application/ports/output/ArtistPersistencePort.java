package com.unmsm.movil.tecnisis.art_galery.application.ports.output;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Person;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;
import java.util.List;
import java.util.Optional;

public interface ArtistPersistencePort {
    static void saveArtist(Person person) {
    }
    List<Request> findRequestsByArtistId(Long artistId);
    Optional<Artist> findById(Long id);
    List<Artist> findAll();
    Artist save(Artist artist);
    void deleteById(Long id);
}
