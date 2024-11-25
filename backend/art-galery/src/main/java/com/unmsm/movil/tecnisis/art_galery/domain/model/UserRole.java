package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserRole {
    private Long userId;
    private String role;
    private Long roleId;
}
