package edu.bu.cs633.minimalsearchengine.exceptions;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ javax.validation.ConstraintViolationException.class,
            edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException.class,
            org.hibernate.exception.ConstraintViolationException.class})
    public ResponseEntity<MSEAPIErrorResponse> constrainViolationExceptionHandler(RuntimeException rex) {

        String message = rex instanceof edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException ?
                ((ConstraintViolationException) rex).getErrorMessage() : rex.getMessage().split("\\.")[1].trim().replace(":", "");

        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), message, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}