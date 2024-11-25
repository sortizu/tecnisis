package com.unmsm.movil.tecnisis.art_galery.infrastructure.adapters.input.rest.controller;

import com.unmsm.movil.tecnisis.art_galery.domain.exception.*;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TechniqueNotFoundException.class)
    public ErrorResponse handleTechniqueNotFoundException() {
        return ErrorResponse.builder()
                .code(TECHNIQUE_NOT_FOUND.getCode())
                .message(TECHNIQUE_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtWorkNotFoundException.class)
    public ErrorResponse handleArtWorkNotFoundException() {
        return ErrorResponse.builder()
                .code(ARTWORK_NOT_FOUND.getCode())
                .message(ARTWORK_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RequestNotFoundException.class)
    public ErrorResponse handleRequestNotFoundException() {
        return ErrorResponse.builder()
                .code(REQUEST_NOT_FOUND.getCode())
                .message(REQUEST_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SpecialistNotFoundException.class)
    public ErrorResponse handleSpecialistNotFoundException() {
        return ErrorResponse.builder()
                .code(SPECIALIST_NOT_FOUND.getCode())
                .message(SPECIALIST_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DocumentNotFoundException.class)
    public ErrorResponse handleDocumentNotFoundException() {
        return ErrorResponse.builder()
                .code(DOCUMENT_NOT_FOUND.getCode())
                .message(DOCUMENT_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtisticEvaluationNotFoundException.class)
    public ErrorResponse handleArtisticEvaluationNotFoundException() {
        return ErrorResponse.builder()
                .code(ARTISTIC_EVALUATION_NOT_FOUND.getCode())
                .message(ARTISTIC_EVALUATION_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EconomicEvaluationNotFoundException.class)
    public ErrorResponse handleEconomicEvaluationNotFoundException() {
        return ErrorResponse.builder()
                .code(ECONOMIC_EVALUATION_NOT_FOUND.getCode())
                .message(ECONOMIC_EVALUATION_NOT_FOUND.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException() {
        return ErrorResponse.builder()
                .code(USER_NOT_FOUND.getCode())
                .message(USER_NOT_FOUND.getMessage())
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
