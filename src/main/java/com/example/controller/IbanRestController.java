package com.example.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.IbanEntity;
import com.example.service.IbanServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * Rest Controller to handle Blacklisted IBANs (stored in H2 repository).
 * http://localhost:8080/api/iban
 * 
 * @author barry.grotjahn
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class IbanRestController {

	// dependency injection through constructor arguments
	private final IbanServiceImpl service;
	
    @GetMapping("/iban")
	public List<IbanEntity> showAll() {
		return service.findAll();
	}

	@GetMapping("/iban/{id}")
	public IbanEntity findById(@PathVariable UUID id) {
		return service.findById(id);
	}
	
	@PostMapping(value="/iban", consumes = {"*/*"})
	@ResponseStatus(HttpStatus.CREATED)
	public IbanEntity create(@RequestBody IbanEntity resource) {
		return service.create(resource);
	}

	@DeleteMapping(value = "/iban/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable UUID id) {
		service.deleteById(id);
	}
}