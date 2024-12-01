package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import java.util.List;
import com.unmsm.movil.tecnisis.art_galery.domain.model.Request;

public interface ArtistServicePort {
    Artist findById(Long id);
    List<Artist> findAll();
    List<Request> findRequestsById(Long id);
    Artist update(Long id, Artist artist);
    void delete(Long id);
}
