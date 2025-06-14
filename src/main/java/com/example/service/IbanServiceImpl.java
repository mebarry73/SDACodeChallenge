package com.example.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.IbanEntity;
import com.example.repository.IbanRepository;

import lombok.RequiredArgsConstructor;

/**
 * The IbanServiceImpl, that uses the IbanRepository.
 * 
 * @author barry.grotjahn
 */
@Service("ibanService")
@RequiredArgsConstructor
public class IbanServiceImpl {

	// dependency injection through constructor arguments
	private final IbanRepository repository;
	
	public String findByIban(String iban) {
		return repository.findEntityByIban(iban);
	}
	
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}
	
	public IbanEntity findById(UUID id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("IBAN not found!"));
	}

	/**
	 * Create a List<IbanEntity> from given Iterable<IbanEntity>.
	 * 
	 * @return actualList
	 */
	public List<IbanEntity> findAll() {
		Iterable<IbanEntity> iterable = repository.findAll();		
		List<IbanEntity> actualList = StreamSupport
				  .stream(iterable.spliterator(), false)
				  .collect(Collectors.toList());
		return actualList;
	}

	/**
	 * Remove all whitespace.
	 * 
	 * @param resource
	 * @return
	 */
	public IbanEntity create(IbanEntity resource) {		
		String noSpace = resource.getIban().replaceAll("\\s", "");
		resource.setIban(noSpace);
		return repository.save(resource);
	}
}