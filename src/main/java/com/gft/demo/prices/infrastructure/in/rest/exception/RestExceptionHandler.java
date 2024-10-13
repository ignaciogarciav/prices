package com.gft.demo.prices.infrastructure.in.rest.exception;

import com.gft.demo.prices.domain.exception.PriceNotFoundException;
import com.gft.demo.prices.infrastructure.in.rest.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final String MISSING_PARAMETER = "Mandatory parameter is not provided";
    private static final String TYPE_MISMATCH = "Provided parameter does not match expected type";
    private static final String CONSTRAINT_VIOLATION = "Provided parameter/s are invalid";
    private static final String ENTITY_NOT_FOUND = "Entity not found by given parameters";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handleMissingServletRequestParameterException(final MissingServletRequestParameterException ex) {
        return buildErrorResponse(MISSING_PARAMETER, List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse(TYPE_MISMATCH, List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException ex) {
        return buildErrorResponse(CONSTRAINT_VIOLATION, ex.getConstraintViolations().stream()
                .map(constraintViolation -> "%s: %s".formatted(constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                .toList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PriceNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException() {
        return buildErrorResponse(ENTITY_NOT_FOUND, new ArrayList<>());
    }

    private ErrorResponse buildErrorResponse(final String message, final List<String> details) {
        return new ErrorResponse().message(message).details(details).timestamp(OffsetDateTime.now());
    }
}
