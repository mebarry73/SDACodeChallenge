package com.example.spi;

import java.io.File;

import org.springframework.web.server.ResponseStatusException;

import com.example.service.IbanServiceImpl;

/**
 * Interface definition for validations (Service Provider Interface).
 * There are many other ways to solve the given problem, but I used that approach.
 * 
 * @author barry.grotjahn
 */
public interface SDAValidation {
	<S> void validateResource(File file, IbanServiceImpl service) throws ResponseStatusException;
}
