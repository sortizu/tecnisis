package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Technique {
    private Long id;
    private String name;
    private String description;
}
