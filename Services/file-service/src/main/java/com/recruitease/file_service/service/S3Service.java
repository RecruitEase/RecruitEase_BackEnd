package com.recruitease.file_service.service;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.recruitease.file_service.DTO.ResponseDTO;
import com.recruitease.file_service.util.CodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public ResponseDTO uploadFile(String folderName,String fileName,MultipartFile file) {
        var responseDTO=new ResponseDTO();
        var content=new HashMap<String,String >();


        try{
            File fileObj = convertMultiPartFileToFile(file);
            String key = folderName  + fileName; // Path to the object in S3
            if(folderName.isEmpty()){
                key=fileName;
            }
            s3Client.putObject(new PutObjectRequest(bucketName, key, fileObj));
            fileObj.delete();

            //if folder is public, give public link, else give saved file folder and file name
            if(folderName.startsWith("public")){
                //use link only if the file is public
                String link="https://"+bucketName+".s3.amazonaws.com/"+key;
                content.put("publicLink",link);
            }else{
                content.put("publicLink",null);
            }


            content.put("path",key);
            content.put("fileName",fileName);
            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("File Uploaded Successfully!");
            responseDTO.setContent(content);
            return responseDTO;
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
            return responseDTO;
        }

    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return convertedFile;
    }

    public S3Object getFile(String keyName) {
        return s3Client.getObject(bucketName, keyName);
    }

    public String generatePresignedUrlServise(String fileName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}