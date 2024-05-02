package com.btv.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> handleException(MyException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Unknown error");
    }
}
