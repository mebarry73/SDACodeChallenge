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
 * Validation implementation that checks if any IBANs in given PDF file is blacklisted. 
 * We use the IBAN service / repository for checking this.
 * But there are 3rd party libraries and 3rd party services we could use instead.
 * 
 * @author barry.grotjahn
 */
@Log
public class BlacklistIbanValidation implements SDAValidation {
	@Override
	public <S> void validateResource(File file, IbanServiceImpl service) throws ResponseStatusException {
		log.info("BlacklistIbanValidation");

		Set<String> allIbansInFile = PDFUtils.getAllIbansInFile(file);
		for (String iban : allIbansInFile) {
			if (service.findByIban(iban) != null) {
				String message = String.format("IBAN %s is Blacklisted!", iban);
				log.log(Level.SEVERE, message);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
			}
		}
	}
}
