package org.rtss.mosad_backend.exception_handler;

import org.rtss.mosad_backend.exceptions.CreditException;
import org.rtss.mosad_backend.exceptions.ObjectNotValidException;
import org.rtss.mosad_backend.exceptions.RepaymentException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleObjectNotValidException(ObjectNotValidException e) {
        return ResponseEntity.badRequest().body(e.getErrorMessages());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleRequestFormatException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RepaymentException.class)
    public ResponseEntity<String> handleRepaymentException(RepaymentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CreditException.class)
    public ResponseEntity<String> handleCreditNotFoundException(CreditException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
