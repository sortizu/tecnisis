package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String password;
}
