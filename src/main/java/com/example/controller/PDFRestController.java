package com.example.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.PDFServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * PDFRestController handling the uploaded PDF, validating it in the PDFServiceImpl.
 * http://localhost:8080/api/pdf-upload
 * 
 * @author barry.grotjahn
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PDFRestController {
	
	// dependency injection through constructor arguments
	private final PDFServiceImpl service;
	
	@PostMapping(path = "/pdf-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Map<String, String>> handleFileUploadUsingCurl(@RequestParam MultipartFile file) throws IOException {

		service.validateResource(file);
		return ResponseEntity.ok().build();
	}
}