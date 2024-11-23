package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;

import java.util.List;

public interface ArtistServicePort {
    Artist findById(Long id);
    List<Artist> findAll();
    List<Request> findRequestsById(Long id);
    Artist save(Artist artist);
    Artist update(Long id, Artist artist);
    void delete(Long id);

}
