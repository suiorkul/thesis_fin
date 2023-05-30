package com.example.qualitycontroll.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.qualitycontroll.dal.entity.AwsDocument;
import com.example.qualitycontroll.dal.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3Client;
    private final DocumentRepository documentRepository;

    public AwsDocument uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        String downloadUrl = s3Object.getObjectContent().getHttpRequest().getURI().toString();
        fileObj.delete();
        log.info("File uploaded : {}",  fileName);
        return documentRepository.save(new AwsDocument().originalFileName(file.getOriginalFilename()).fileName(fileName).fileUrl(downloadUrl));
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

}
