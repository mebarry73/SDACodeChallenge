package com.example.utils;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.java.Log;

/**
 * This class handles all the PDF processing.
 * 
 * @author barry.grotjahn
 */
@Log
public class PDFUtils {
	
	public static Set<String> getAllIbansInFile(File f) {
		String parsedText;
		PDFParser parser;
		PDDocument pdfDocument = null;
		HashSet<String> ibanSet = new HashSet<String>();
		
		try {
			parser = new PDFParser(new RandomAccessReadBufferedFile(f));
			pdfDocument = parser.parse();

			int numberOfPages = pdfDocument.getNumberOfPages();
			for (int i = 0; i < numberOfPages; i++) {
				//PDPage page = pdfDocument.getPage(i);
				//String billing = getBillingNumber(page);
				PDFTextStripper reader = new PDFTextStripper();
				reader.setStartPage(i);
				reader.setEndPage(i);
				parsedText = reader.getText(pdfDocument);
				Pattern p = Pattern.compile(Pattern.quote("IBAN:") + "(.*?)" + Pattern.quote("SWIFT/BIC"));
				Matcher m = p.matcher(parsedText);
				while (m.find()) {
					System.err.println(m.group(1).replaceAll("\\s", ""));
					
					ibanSet.add(m.group(1).replaceAll("\\s", ""));
				}
			}
			pdfDocument.close();
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} finally {
			try {
				pdfDocument.close();
			} catch (IOException e) {
				log.log(Level.SEVERE, e.getMessage());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}			
		}
				
		return ibanSet;
	}

	/**
	 * Unused method, but we could use the billing number to store what customer delivers blacklisted IBANs.
	 * We could trace also by address or name of the doctor and store all into a repository for future reporting. 
	 * 
	 * @param pd
	 * @return
	 * @throws IOException
	 */
	public static String getBillingNumber(PDPage pd) throws IOException {
		Rectangle invoiceRectangle = new Rectangle(60, 740, 150, 750); // area that contains billing number
		PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
		pdfTextStripperByArea.addRegion("BillingNumber", invoiceRectangle);
		pdfTextStripperByArea.extractRegions(pd);
		String textForRegion = pdfTextStripperByArea.getTextForRegion("BillingNumber");
		
		// 05-4711-000011-4
		Pattern p = Pattern.compile("\\d{2}-\\d{4}-\\d{6}-\\d{4}");
		Matcher m = p.matcher(textForRegion);
		while (m.find()) {
		}
		
		return null;
	}
}
