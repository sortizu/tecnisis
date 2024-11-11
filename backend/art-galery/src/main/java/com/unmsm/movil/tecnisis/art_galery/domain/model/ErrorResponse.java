package com.unmsm.movil.tecnisis.art_galery.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> details;
    private LocalDateTime timeStamp;
}
