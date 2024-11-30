package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

import java.util.List;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Specialist {
    private Long id;
    private Boolean isAvailable;
    private Person person;
    List<Technique> techniques;
}
