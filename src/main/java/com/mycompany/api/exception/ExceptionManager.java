package com.mycompany.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionManager {

    @ExceptionHandler({EmptyResultException.class, NullValueException.class})
    public ResponseEntity<String> handleException(Exception e) {

        if (e instanceof EmptyResultException) {
            return ResponseEntity.ok("No messages found");
        }
        if (e instanceof NullValueException) {
            return ResponseEntity.badRequest().body("no value can be null");
        }
        return ResponseEntity.badRequest().body("undefined exception");

    }
}
