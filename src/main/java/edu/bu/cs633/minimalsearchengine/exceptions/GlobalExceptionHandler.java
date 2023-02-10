package edu.bu.cs633.minimalsearchengine.exceptions;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<MSEAPIErrorResponse> customHandleNotFoundExceptionHandler(NotFoundException notFound) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), notFound.getErrorMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNameNotFoundException.class)
    public ResponseEntity<MSEAPIErrorResponse> usernameNotFoundExceptionHandler(UserNameNotFoundException e) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), e.getErrorMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PasswordDidNotMatchException.class)
    public ResponseEntity<MSEAPIErrorResponse> passwordDidNotMatchExceptionHandler(PasswordDidNotMatchException pass) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), pass.getErrorMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidURLException.class)
    public ResponseEntity<MSEAPIErrorResponse> invalidURLExceptionHandler(InvalidURLException ex) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), ex.getErrorMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<MSEAPIErrorResponse> missingParams(MissingServletRequestParameterException ex) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ExistingIndexedURLException.class)
    public ResponseEntity<MSEAPIErrorResponse> pageAlreadyExistsExceptionHandler(ExistingIndexedURLException url) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), url.getErrorMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MSEAPIErrorResponse> typeMismatchHandler(MethodArgumentTypeMismatchException typeMismatch) {
        MSEAPIErrorResponse errorResponse = new MSEAPIErrorResponse(LocalDateTime.now(), typeMismatch.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<MSEAPIErrorResponse> argumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new MSEAPIErrorResponse(LocalDateTime.now(), errors.toString(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

}