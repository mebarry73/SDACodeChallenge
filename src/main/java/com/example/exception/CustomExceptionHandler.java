package com.example.exception;

import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.java.Log;

/**
 * CustomExceptionHandler for dealing with all ResponseStatusExceptions.
 * 
 * @author barry.grotjahn
 */
@RestControllerAdvice
@Log
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
    	log.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}