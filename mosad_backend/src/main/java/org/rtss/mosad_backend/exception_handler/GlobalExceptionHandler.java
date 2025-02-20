package org.rtss.mosad_backend.exception_handler;

import org.rtss.mosad_backend.exceptions.DbTableInitException;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<Set<String>> handleObjectNotValidException(ObjectNotValidException e) {
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleRequestFormatException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DbTableInitException.class)
    public ResponseEntity<String> handleRequestFormatException(DbTableInitException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
