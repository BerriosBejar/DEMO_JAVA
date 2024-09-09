package com.backend.api.demo.integracion.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.backend.api.demo.integracion.exception.dto.ErrorResponseLogin;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
    
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseLogin> handleResponseStatusException(ResponseStatusException ex) {
		ErrorResponseLogin errorResponse = new ErrorResponseLogin(ex.getReason());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
	
}
