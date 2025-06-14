package com.example.spi;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.service.IbanServiceImpl;
import com.example.utils.PDFUtils;

import lombok.extern.java.Log;

/**
 * Validation implementation that checks if all IBANs in given PDF file are in correct format (German IBAN).
 * Depending on the country the structure is different, so we here focus on German IBANs.
 * https://www.iban.com/structure
 * 
 * @author barry.grotjahn
 */
@Log
public class GermanIbanValidation implements SDAValidation {
	@Override
	public <S> void validateResource(File file, IbanServiceImpl service) throws ResponseStatusException {
		log.info("GermanIbanValidation");
		
		Set<String> allIbansInFile = PDFUtils.getAllIbansInFile(file);
		for(String iban : allIbansInFile) {
			if(!iban.matches("^DE\\d{20}$")) {
		    	log.log(Level.SEVERE, "IBAN is is not of German structure.");
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IBAN is is not of German structure.");    					
			}
		}
	}
}