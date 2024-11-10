package com.unmsm.movil.tecnisis.art_galery.application.ports.input;

import com.unmsm.movil.tecnisis.art_galery.domain.model.Artist;
import java.util.List;

public interface ArtistServicePort {
    Artist findArtistById(Long id);
    List<Artist> findAll();
    Artist saveArtist(Artist artist);
    Artist updateArtist(Long id, Artist artist);
    void deleteArtist(Long id);
}
