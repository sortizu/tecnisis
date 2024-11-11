package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.domain.exception.ArtistNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.exception.PersonNotFoundException;
import com.unmsm.movil.tecnisis.art_galery.domain.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;

import static com.unmsm.movil.tecnisis.art_galery.utils.ErrorCatalog.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtistNotFoundException.class)
    public ErrorResponse handleArtistNotFoundException() {
        return ErrorResponse.builder()
                .code(ARTIST_NOT_FOUND.getCode())
                .message(ARTIST_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotFoundException.class)
    public ErrorResponse handlePersonNotFoundException() {
        return ErrorResponse.builder()
                .code(PERSON_NOT_FOUND.getCode())
                .message(PERSON_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ){
        BindingResult result = exception.getBindingResult();
        return ErrorResponse.builder()
                .code(INVALID_PARAMETER.getCode())
                .message(INVALID_PARAMETER.getMessage())
                .details(result.getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .toList()
                )
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse  handleGenericException(Exception exception) {
        return ErrorResponse.builder()
                .code(GENERIC_ERROR.getCode())
                .message(GENERIC_ERROR.getMessage())
                .details(List.of(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .build();
    }

}