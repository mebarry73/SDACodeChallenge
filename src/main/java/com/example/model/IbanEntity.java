package com.example.model;

import java.util.UUID;

import com.example.utils.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * Entity for the IBAN.
 * Builder can be used in unit tests for manual creating IbanEntity (mock).
 * 
 * @author barry.grotjahn
 */
@Builder
@Data
@Entity(name = "iban")
@Table
public class IbanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique=true)
	private String iban;
	
	public IbanEntity() {
	}
		
	public IbanEntity(UUID id, String iban) {
		this.id = id;
		this.iban = iban;
	}

	public String toString() {
		return new StringBuffer().append(id).append(Constants.CSV_DELIMITER).append(iban).toString();
	}
}