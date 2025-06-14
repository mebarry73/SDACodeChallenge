package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.model.IbanEntity;

/**
 * The repository for the Blacklist IBAN entity.
 * 
 * @author barry.grotjahn
 */
public interface IbanRepository extends CrudRepository<IbanEntity, UUID> {
	/**
	 * Custom SQL query to check if given IBAN exists in repository.
	 * 
	 * @param iban
	 * @return String 
	 */
	@Query("SELECT i.iban from iban i WHERE i.iban=:iban")
	String findEntityByIban(@Param("iban") String iban);
}
