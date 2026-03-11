package com.ey.azure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

@Service
public class AzureBlobService {
	
	@Value("${azure.storage.connection-string}")
	private String connectionString;
	
	@Value("${azure.storage.container-name}")
	private String containerName;
	
	public String uploadFile(MultipartFile file) {
		try {
			BlobServiceClient blobServiceClient= new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
			
			BlobContainerClient containerClient= blobServiceClient.getBlobContainerClient(containerName);
			
			if(!containerClient.exists()) {
				containerClient.create();
			}
			
			String blobName= System.currentTimeMillis()+"_"+file.getOriginalFilename();
			
			BlobClient blobClient = containerClient.getBlobClient(blobName);
			
			blobClient.upload(file.getInputStream(),file.getSize(), true);
			
			return blobClient.getBlobUrl();
		}catch(Exception e) {
			throw new RuntimeException("Error uploading file: "+ e.getMessage());
		}
	}
	

}
