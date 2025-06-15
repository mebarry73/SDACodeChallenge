package com.example.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.UUID;
import java.util.logging.Level;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.spi.SDAValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

/**
 * The PDFServiceImpl service, that is doing all validations 
 * (using SPI, defined under META-INF/services/com.example.spi.SDAValidation).
 * This is an example of how to extend as mentioned in the task.
 * 
 * What should be optimized is to introduce some kind of caching, so we not read the PDF again and again, 
 * as this is an performance issue.
 * 
 * @author barry.grotjahn
 */
@Log
@Service
@RequiredArgsConstructor
@DependsOn("ibanService")
public class PDFServiceImpl {
	
	// dependency injection through constructor arguments
	private final IbanServiceImpl ibanService;
	
	public void validateResource(MultipartFile multipartFile) {
		// tmp file that will be deleted after processing.		
		File file = new File(UUID.randomUUID().toString().concat(".pdf"));

		try (OutputStream os = new FileOutputStream(file)) {
			os.write(multipartFile.getBytes());
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);			
		}		
		
		try {
			ServiceLoader.load(SDAValidation.class).stream().map(ServiceLoader.Provider::get).forEach(validator -> validator.validateResource(file, ibanService));			
        } catch (ServiceConfigurationError serviceError) {
			log.log(Level.SEVERE, serviceError.getMessage(), serviceError);			        	
        } finally {
			file.delete();
		}		
	}
}