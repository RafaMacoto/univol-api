package com.univol.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ValidationError(String field, String message) {
        public ValidationError(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }


    record GenericError(String message, int status) {}


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handle(MethodArgumentNotValidException e) {
        return e.getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .toList();
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericError handle(ConstraintViolationException e) {
        return new GenericError(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus
    public GenericError handle(ResponseStatusException e) {
        return new GenericError(e.getReason(), e.getStatusCode().value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericError handle(Exception e) {
        return new GenericError("Erro interno inesperado.", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
