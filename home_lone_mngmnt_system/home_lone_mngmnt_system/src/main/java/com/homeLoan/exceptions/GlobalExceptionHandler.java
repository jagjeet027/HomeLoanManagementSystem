package com.homeLoan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handleInvalid(InvalidRequestException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoanException.class)
    public ResponseEntity<?> handleLoan(LoanException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobal(Exception ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}