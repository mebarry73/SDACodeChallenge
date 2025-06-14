package com.example.spi;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.service.IbanServiceImpl;

import lombok.extern.java.Log;

/**
 * Validation implementation that checks if given file is a PDF file.
 * We use org.apache.pdfbox for that.
 * 
 * @author barry.grotjahn
 */
@Log
public class PDFValidation implements SDAValidation {
	@Override
	public <S> void validateResource(File file, IbanServiceImpl service) throws ResponseStatusException {
	    try (PDDocument document = Loader.loadPDF(file)) {
	    } catch (IOException ioe) {
	    	log.log(Level.SEVERE, "File is not a PDF file.", ioe);
	    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is not a PDF file.");    	
	    }		
	}
}