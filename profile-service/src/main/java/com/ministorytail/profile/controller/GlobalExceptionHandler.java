package com.ministorytail.profile.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ministorytail.profile.model.exceptions.ChildAlreadyExistsException;
import com.ministorytail.profile.model.exceptions.ChildNotFoundException;
import com.ministorytail.profile.model.exceptions.MonthlyLimitExceededException;
import com.ministorytail.profile.model.exceptions.ProfileNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Classe interna para resposta de erro padronizada
     */
    private static class ErrorResponse {
        private final LocalDateTime timestamp;
        private final int status;
        private final String error;
        private final String message;
        private final Map<String, String> details;

        public ErrorResponse(HttpStatus status, String message, Map<String, String> details) {
            this.timestamp = LocalDateTime.now();
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.details = details;
        }

        public ErrorResponse(HttpStatus status, String message) {
            this(status, message, null);
        }

        // Getters
        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public Map<String, String> getDetails() {
            return details;
        }
    }

    /**
     * Trata ProfileNotFoundException
     * Retorna 404 NOT FOUND
     */
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFound(ProfileNotFoundException ex) {
        log.error("Profile not found: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata ChildNotFoundException
     * Retorna 404 NOT FOUND
     */
    @ExceptionHandler(ChildNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleChildNotFound(ChildNotFoundException ex) {
        log.error("Child not found: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata ChildAlreadyExistsException
     * Retorna 409 CONFLICT
     */
    @ExceptionHandler(ChildAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleChildAlreadyExists(ChildAlreadyExistsException ex) {
        log.error("Child already exists: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Trata MonthlyLimitExceededException
     * Retorna 429 TOO MANY REQUESTS
     */
    @ExceptionHandler(MonthlyLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleMonthlyLimitExceeded(MonthlyLimitExceededException ex) {
        log.error("Monthly limit exceeded: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.TOO_MANY_REQUESTS,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(error);
    }

    /**
     * Trata IllegalArgumentException (validações de domínio)
     * Retorna 400 BAD REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Invalid argument: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata IllegalStateException (violações de estado)
     * Retorna 409 CONFLICT
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
        log.error("Invalid state: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Trata erros de validação (@Valid)
     * Retorna 400 BAD REQUEST com detalhes dos campos inválidos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceções genéricas não mapeadas
     * Retorna 500 INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error: ", ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please contact support.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}