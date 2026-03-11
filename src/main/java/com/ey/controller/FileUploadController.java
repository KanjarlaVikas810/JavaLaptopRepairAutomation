package com.ey.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ey.azure.AzureBlobService;

@RestController
@RequestMapping("/laptop-service/technician/files")
public class FileUploadController {
	
	private final AzureBlobService azureBlobService;
	
	public FileUploadController(AzureBlobService azureBlobService) {
		super();
		this.azureBlobService = azureBlobService;
	}
	
	@PostMapping("/request")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
		String imageUrl=azureBlobService.uploadFile(file);
		return ResponseEntity.ok(imageUrl);
	}

	
	
	
	
	
	
	

}
